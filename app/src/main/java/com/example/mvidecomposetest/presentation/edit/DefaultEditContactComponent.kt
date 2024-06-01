package com.example.mvidecomposetest.presentation.edit

import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    private val contact: Contact,
    storage: Repository = ContactsStorage,
) : EditContactComponent {
    private val editContactUseCase = EditContactUseCase(storage)
    private val initState =
        EditContactComponent.Model(userName = contact.userName, mobilePhone = contact.mobilePhone)

    private val _state = MutableStateFlow(initState)

    override val state: StateFlow<EditContactComponent.Model>
        get() = _state.asStateFlow()

    override fun onUpdateUserName(userName: String) {
        _state.value = state.value.copy(userName = userName)
    }

    override fun onUpdateMobilePhone(mobilePhone: String) {
        _state.value = state.value.copy(mobilePhone = mobilePhone)
    }

    override fun onSave(model: EditContactComponent.Model) {
        val (userName, mobilePhone) = model
        editContactUseCase(contact.copy(userName = userName, mobilePhone = mobilePhone))
    }
}