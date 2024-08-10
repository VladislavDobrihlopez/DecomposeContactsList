package com.example.mvidecomposetest.presentation.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.mvidecomposetest.presentation.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultEditContactComponent(
    private val componentContext: ComponentContext,
    private val onSaveSuccessfully: () -> Unit,
) : EditContactComponent, ComponentContext by componentContext {

    private lateinit var store: EditContactStore

    init {
        componentScope.launch {
            store.labels.collect { label ->
                when (label) {
                    EditContactStore.Label.OnContactSaved -> onSaveSuccessfully()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<EditContactComponent.Model>
        get() = store.stateFlow

    override fun onUpdateUserName(userName: String) {
        store.accept(EditContactStore.Intent.UsernameChanged(username = userName))
    }

    override fun onUpdateMobilePhone(mobilePhone: String) {
        store.accept(EditContactStore.Intent.PhoneChanged(phone = mobilePhone))
    }

    override fun onSave() {
        store.accept(EditContactStore.Intent.Confirm)
    }
}
