package com.example.mvidecomposetest.presentation.list

import com.example.mvidecomposetest.domain.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactsListComponent {
    val state: StateFlow<Model>

    fun onAddNewClick()
    fun onContactClick(contact: Contact)

    data class Model(val contacts: List<Contact>)
}