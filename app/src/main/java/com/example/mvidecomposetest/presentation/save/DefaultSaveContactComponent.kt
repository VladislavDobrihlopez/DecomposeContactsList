package com.example.mvidecomposetest.presentation.save

import com.example.mvidecomposetest.byDefault
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.AddContactUseCase
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultSaveContactComponent(
    storage: Repository = ContactsStorage,
) : SaveContactComponent {
    private val saveContactUseCase = AddContactUseCase(storage)
    private val initState =
        SaveContactComponent.Model(userName = String.byDefault, mobilePhone = String.byDefault)

    private val _state = MutableStateFlow(initState)

    override val state: StateFlow<SaveContactComponent.Model>
        get() = _state.asStateFlow()

    override fun onUpdateUserName(userName: String) {
        _state.value = state.value.copy(userName = userName)
    }

    override fun onUpdateMobilePhone(mobilePhone: String) {
        _state.value = state.value.copy(mobilePhone = mobilePhone)
    }

    override fun onSave(model: SaveContactComponent.Model) {
        val (userName, mobilePhone) = model
        saveContactUseCase(userName, mobilePhone)
    }
}
