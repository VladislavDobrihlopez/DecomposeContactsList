package com.example.mvidecomposetest.presentation.save

import com.arkivanov.mvikotlin.core.store.Store

interface SaveContactStore : Store<SaveContactStore.Intent, SaveContactComponent.Model, SaveContactStore.Label> {

    sealed interface Intent {
        data class UsernameInputted(val username: String) : Intent
        data class PhoneInputted(val phone: String) : Intent
        data object Save: Intent
    }

    sealed interface Label {
        data object OnContactSaved: Label
    }
}
