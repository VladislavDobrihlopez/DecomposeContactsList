package com.example.mvidecomposetest.presentation.save

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import com.example.mvidecomposetest.byDefault
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.AddContactUseCase
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultSaveContactComponent(
    storage: Repository = ContactsStorage,
    componentContext: ComponentContext,
    private val saveContactUseCase: AddContactUseCase = AddContactUseCase(storage),
    private val onSaveSuccessfully: () -> Unit,
) : SaveContactComponent, ComponentContext by componentContext {
    companion object {
        private val SCREEN_KEY = DefaultSaveContactComponent::class.java.simpleName
    }

    init {
        stateKeeper.register(SCREEN_KEY) {
            state.value
        }
    }

    private val initState = stateKeeper.consume(SCREEN_KEY) ?:
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
        onSaveSuccessfully()
    }
}
