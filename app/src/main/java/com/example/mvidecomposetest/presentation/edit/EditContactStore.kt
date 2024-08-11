package com.example.mvidecomposetest.presentation.edit

import com.arkivanov.mvikotlin.core.store.Store

typealias EditContactStoreState = EditContactComponent.Model

interface EditContactStore: Store<EditContactStore.Intent, EditContactStoreState, EditContactStore.Label> {

    sealed interface Intent {
        data class UsernameChanged(val username: String): Intent
        data class PhoneChanged(val phone: String): Intent
        data object Confirm: Intent
    }

    sealed interface Label {
        data object OnContactSaved: Label
    }
}
