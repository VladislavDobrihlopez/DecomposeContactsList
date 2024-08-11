package com.example.mvidecomposetest.presentation.edit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import com.example.mvidecomposetest.domain.Repository

class EditContactStoreFactory {
    companion object {
        private val NAME = EditContactStoreFactory::class.java.simpleName
    }

    private val repository: Repository = ContactsStorage
    private val storeFactory: StoreFactory = LoggingStoreFactory(DefaultStoreFactory())
    private val editContactUseCase: EditContactUseCase = EditContactUseCase(repository)

    fun create(contact: Contact): EditContactStore = object : EditContactStore,
        Store<EditContactStore.Intent, EditContactStoreState, EditContactStore.Label> by storeFactory.create(
            name = NAME,
            initialState = EditContactComponent.Model(
                id = contact.id,
                userName = contact.username,
                mobilePhone = contact.mobilePhone
            ),
            reducer = ReducerImpl,
            executorFactory = {
                ExecutorImpl()
            }
        ) {}

    private inner class ExecutorImpl: CoroutineExecutor<EditContactStore.Intent, Nothing, EditContactStoreState, Message, EditContactStore.Label>() {

        override fun executeIntent(
            intent: EditContactStore.Intent,
            getState: () -> EditContactStoreState,
        ) {
            when (intent) {
                EditContactStore.Intent.Confirm -> {
                    val state = getState()
                    editContactUseCase(
                        Contact(
                            id = state.id,
                            username = state.userName,
                            mobilePhone = state.mobilePhone
                        )
                    )
                    publish(EditContactStore.Label.OnContactSaved)
                }
                is EditContactStore.Intent.PhoneChanged -> {
                    dispatch(Message.PhoneChanged(phone = intent.phone))
                }
                is EditContactStore.Intent.UsernameChanged -> {
                    dispatch(Message.UsernameChanged(username = intent.username))
                }
            }
        }
    }

    private sealed interface Message {
        data class UsernameChanged(val username: String) : Message
        data class PhoneChanged(val phone: String) : Message
    }

    private object ReducerImpl : Reducer<EditContactStoreState, Message> {
        override fun EditContactStoreState.reduce(msg: Message): EditContactStoreState {
            return when (msg) {
                is Message.PhoneChanged -> this.copy(mobilePhone = msg.phone)
                is Message.UsernameChanged -> this.copy(userName = msg.username)
            }
        }
    }
}
