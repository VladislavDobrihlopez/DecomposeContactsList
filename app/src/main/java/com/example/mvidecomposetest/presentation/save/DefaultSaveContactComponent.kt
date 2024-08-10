package com.example.mvidecomposetest.presentation.save

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.mvidecomposetest.presentation.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSaveContactComponent(
    componentContext: ComponentContext,
    private val onSaveSuccessfully: () -> Unit,
) : SaveContactComponent, ComponentContext by componentContext {

    private lateinit var store: SaveContactStore

    init {
        componentScope.launch {
            store.labels.collect { label ->
                when (label) {
                    SaveContactStore.Label.OnContactSaved -> onSaveSuccessfully()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<SaveContactComponent.Model>
        get() = store.stateFlow

    override fun onUpdateUserName(userName: String) {
        store.accept(SaveContactStore.Intent.UsernameInputted(username = userName))
    }

    override fun onUpdateMobilePhone(mobilePhone: String) {
        store.accept(SaveContactStore.Intent.PhoneInputted(phone = mobilePhone))
    }

    override fun onSave() {
        store.accept(SaveContactStore.Intent.Save)
    }
}
