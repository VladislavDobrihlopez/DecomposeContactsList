package com.example.mvidecomposetest.presentation.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.launch

class ContactsListStoreFactory(
    private val storeFactory: StoreFactory,
    private val getContactsUseCase: GetContactsUseCase,
) {
    companion object {
        private val NAME = ContactsListStore::class.java.simpleName
    }

    fun create(): ContactsListStore = object : ContactsListStore,
        Store<ContactsListStore.Intent, ContactsListStore.State, ContactsListStore.Label> by storeFactory.create(
            name = NAME,
            initialState = ContactsListStore.State(listOf()),
            bootstrapper = BootstrapImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        )  {}

    private sealed interface Action {
        data class UpdateContacts(val items: List<Contact>): Action
    }

    private inner class BootstrapImpl: CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getContactsUseCase().collect { contacts ->
                    dispatch(Action.UpdateContacts(contacts))
                }
            }
        }
    }

    private inner class ExecutorImpl: CoroutineExecutor<ContactsListStore.Intent, Action, ContactsListStore.State, Message, ContactsListStore.Label>() {
        override fun executeIntent(intent: ContactsListStore.Intent) {
            super.executeIntent(intent)
            when (intent) {
                ContactsListStore.Intent.AddNewClick -> publish(ContactsListStore.Label.OnAddContactRequested)
                is ContactsListStore.Intent.ContactClick -> publish(ContactsListStore.Label.OnEditingContactRequested(intent.contact))
            }
        }

        override fun executeAction(action: Action) {
            super.executeAction(action)
            when (action) {
                is Action.UpdateContacts -> dispatch(Message.Content(action.items))
            }
        }
    }

    private object ReducerImpl: Reducer<ContactsListStore.State, Message> {
        override fun ContactsListStore.State.reduce(msg: Message): ContactsListStore.State {
            return when (msg) {
                is Message.Content -> copy(contacts = msg.contact)
            }
        }
    }

    private sealed interface Message {
        data class Content(val contact: List<Contact>): Message
    }
}