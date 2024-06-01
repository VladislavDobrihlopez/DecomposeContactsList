package com.example.mvidecomposetest.presentation.list

import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.GetContactsUseCase
import com.example.mvidecomposetest.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactsListComponent(
    storage: Repository = ContactsStorage,
    private val onAddContactRequested: () -> Unit,
    private val onEditingContactRequested: (Contact) -> Unit,
) : ContactsListComponent {
    private val getContactsUseCase = GetContactsUseCase(storage)
    private val initState = ContactsListComponent.Model(listOf())
    private val scope = CoroutineScope(Dispatchers.Main.immediate)

    override val state = getContactsUseCase()
        .map { storedContacts ->
            ContactsListComponent.Model(storedContacts)
        }
        .stateIn(scope, SharingStarted.WhileSubscribed(500), initState)

    override fun onAddNewClick() {
        onAddContactRequested()
    }

    override fun onContactClick(contact: Contact) {
        onEditingContactRequested(contact)
    }
}