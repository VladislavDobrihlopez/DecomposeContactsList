package com.example.mvidecomposetest.presentation.list

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.mvikotlin.core.store.Store
import com.example.mvidecomposetest.domain.Contact

interface ContactsListStore: Store<ContactsListStore.Intent, ContactsListStore.State, ContactsListStore.Label>, InstanceKeeper.Instance {

    sealed interface Intent {
        data object AddNewClick: Intent
        data class ContactClick(val contact: Contact): Intent
    }

    data class State(val contacts: List<Contact>)

    sealed interface Label {
        data object OnAddContactRequested: Label
        data class OnEditingContactRequested(val contact: Contact): Label
    }
}
