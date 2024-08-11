package com.example.mvidecomposetest.presentation.save

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.AddContactUseCase
import com.example.mvidecomposetest.domain.Repository

// In case of using DI dependencies must be injected
class SaveContactStoreFactory {
    companion object {
        private val NAME = SaveContactStoreFactory::class.java.simpleName
    }

    private val repository: Repository = ContactsStorage
    private val storeFactory: StoreFactory = DefaultStoreFactory()
    private val addContactUseCase: AddContactUseCase = AddContactUseCase(repository)

    private val store: Store<SaveContactStore.Intent, SaveContactStoreState, SaveContactStore.Label> =
        storeFactory.create(
            name = NAME,
            executorFactory = { ExecutorImpl() },
            initialState = SaveContactComponent.Model("", ""),
            reducer = ReducerImpl,
        )

    fun create(): SaveContactStore = object: SaveContactStore, Store<SaveContactStore.Intent, SaveContactStoreState, SaveContactStore.Label> by store {}

    private inner class ExecutorImpl : CoroutineExecutor<SaveContactStore.Intent, Nothing, SaveContactStoreState, Message, SaveContactStore.Label>() {
        override fun executeIntent(
            intent: SaveContactStore.Intent,
            getState: () -> SaveContactStoreState,
        ) {
            super.executeIntent(intent, getState)
            when (intent) {
                is SaveContactStore.Intent.PhoneInputted -> {
                    dispatch(Message.PhoneInputted(phone = intent.phone))
                }

                is SaveContactStore.Intent.UsernameInputted -> {
                    dispatch(Message.UsernameInputted(username = intent.username))
                }

                SaveContactStore.Intent.Save -> {
                    val state = getState()
                    addContactUseCase(username = state.userName, phone = state.mobilePhone)
                    publish(SaveContactStore.Label.OnContactSaved)
                }
            }
        }
    }

    sealed interface Message {
        data class UsernameInputted(val username: String) : Message
        data class PhoneInputted(val phone: String) : Message
    }

    private object ReducerImpl : Reducer<SaveContactStoreState, Message> {
        override fun SaveContactStoreState.reduce(msg: Message): SaveContactStoreState {
            return when (msg) {
                is Message.PhoneInputted -> copy(mobilePhone = msg.phone)
                is Message.UsernameInputted -> copy(userName = msg.username)
            }
        }
    }
}
