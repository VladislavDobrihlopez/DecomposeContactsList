package com.example.mvidecomposetest.presentation.list

import android.os.Parcelable
import com.example.mvidecomposetest.domain.Contact
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface ContactsListComponent {
    val state: StateFlow<Model>

    fun onAddNewClick()
    fun onContactClick(contact: Contact)

    @Parcelize
    data class Model(val contacts: List<Contact>): Parcelable
}