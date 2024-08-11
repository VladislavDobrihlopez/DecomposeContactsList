package com.example.mvidecomposetest.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.presentation.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultContactsListComponent(
    private val componentContext: ComponentContext,
    private val onAddContactRequested: () -> Unit,
    private val onEditingContactRequested: (Contact) -> Unit,
) : ContactsListComponent, ComponentContext by componentContext {

    private val initState = ContactsListComponent.Model(listOf())
    private lateinit var store: ContactsListStore

    init {
        store = ContactsListStoreFactory().create()
        componentScope.launch {
            store.labels.collect { label ->
                when (label) {
                    ContactsListStore.Label.OnAddContactRequested -> onAddContactRequested()
                    is ContactsListStore.Label.OnEditingContactRequested -> onEditingContactRequested(
                        label.contact
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val state: StateFlow<ContactsListComponent.Model>
        get() {
            return store.stateFlow.map { storedContacts ->
                ContactsListComponent.Model(storedContacts.contacts)
            }.stateIn(componentScope, SharingStarted.WhileSubscribed(500), initState)
        }

    override fun onAddNewClick() {
        store.accept(ContactsListStore.Intent.AddNewClick)
    }

    override fun onContactClick(contact: Contact) {
        store.accept(ContactsListStore.Intent.ContactClick(contact = contact))
    }
}
