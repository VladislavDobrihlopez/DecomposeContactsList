package com.example.mvidecomposetest.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.example.mvidecomposetest.data.ContactsStorage
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.GetContactsUseCase
import com.example.mvidecomposetest.domain.Repository
import com.example.mvidecomposetest.presentation.componentScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DefaultContactsListComponent(
    storage: Repository = ContactsStorage,
    private val componentContext: ComponentContext,
    private val getContactsUseCase: GetContactsUseCase = GetContactsUseCase(storage),
    private val onAddContactRequested: () -> Unit,
    private val onEditingContactRequested: (Contact) -> Unit,
) : ContactsListComponent, ComponentContext by componentContext {
    private val initState = ContactsListComponent.Model(listOf())

    override val state = getContactsUseCase()
        .map { storedContacts ->
            ContactsListComponent.Model(storedContacts)
        }
        .stateIn(componentScope, SharingStarted.WhileSubscribed(500), initState)

    override fun onAddNewClick() {
        onAddContactRequested()
    }

    override fun onContactClick(contact: Contact) {
        onEditingContactRequested(contact)
    }
}
