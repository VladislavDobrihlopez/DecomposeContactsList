package com.example.mvidecomposetest.presentation.edit

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase

class EditContactStoreFactory(
    private val storeFactory: StoreFactory,
    private val editContactUseCase: EditContactUseCase,
) {
    companion object {
        private val NAME = EditContactStoreFactory::class.java.simpleName
    }

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
        override fun executeIntent(intent: EditContactStore.Intent) {
            when (intent) {
                EditContactStore.Intent.Confirm -> {
                    val state = state()
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