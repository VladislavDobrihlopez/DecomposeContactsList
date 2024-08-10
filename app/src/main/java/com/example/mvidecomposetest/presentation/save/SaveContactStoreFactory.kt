package com.example.mvidecomposetest.presentation.save

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.mvidecomposetest.domain.AddContactUseCase

class SaveContactStoreFactory(
    private val storeFactory: StoreFactory,
    private val addContactUseCase: AddContactUseCase,
) {
    companion object {
        private val NAME = SaveContactStoreFactory::class.java.simpleName
    }

    private val store: Store<SaveContactStore.Intent, SaveContactStoreState, SaveContactStore.Label> =
        storeFactory.create(
            name = NAME,
            executorFactory = { ExecutorImpl() },
            initialState = SaveContactComponent.Model("", ""),
            reducer = ReducerImpl,
        )

    fun create(): SaveContactStore = object: SaveContactStore, Store<SaveContactStore.Intent, SaveContactStoreState, SaveContactStore.Label> by store {}

    private inner class ExecutorImpl : CoroutineExecutor<SaveContactStore.Intent, Nothing, SaveContactStoreState, Message, SaveContactStore.Label>() {
        override fun executeIntent(intent: SaveContactStore.Intent) {
            when (intent) {
                is SaveContactStore.Intent.PhoneInputted -> {
                    dispatch(Message.PhoneInputted(phone = intent.phone))
                }

                is SaveContactStore.Intent.UsernameInputted -> {
                    dispatch(Message.UsernameInputted(username = intent.username))
                }

                SaveContactStore.Intent.Save -> {
                    val state = state()
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
