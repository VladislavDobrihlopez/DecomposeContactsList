package com.example.mvidecomposetest.presentation.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    storage: Repository = ContactsStorage,
    private val componentContext: ComponentContext,
    private val contact: Contact,
    private val onSaveSuccessfully: () -> Unit,
) : EditContactComponent, ComponentContext by componentContext {
    companion object {
        private val SCREEN_KEY = DefaultEditContactComponent::class.java.simpleName
    }

    init {
        stateKeeper.register(SCREEN_KEY) {
            state.value
        }
    }

    private val editContactUseCase = EditContactUseCase(storage)
    private val initState = stateKeeper.consume(SCREEN_KEY) ?: EditContactComponent.Model(userName = contact.userName, mobilePhone = contact.mobilePhone)

    private val _state = MutableStateFlow(initState)

    override val state: StateFlow<EditContactComponent.Model>
        get() = _state.asStateFlow()

    override fun onUpdateUserName(userName: String) {
        _state.value = state.value.copy(userName = userName)
    }

    override fun onUpdateMobilePhone(mobilePhone: String) {
        _state.value = state.value.copy(mobilePhone = mobilePhone)
    }

    override fun onSave() {
        val (userName, mobilePhone) = state.value
        editContactUseCase(contact.copy(userName = userName, mobilePhone = mobilePhone))
        onSaveSuccessfully()
    }
}