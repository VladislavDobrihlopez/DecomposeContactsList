package com.example.mvidecomposetest.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.mvidecomposetest.presentation.edit.EditContactComponent
import com.example.mvidecomposetest.presentation.list.ContactsListComponent
import com.example.mvidecomposetest.presentation.save.SaveContactComponent

interface RootComponent {
    val state: Value<ChildStack<*, Child>>

    sealed class Child {
        data class AddContactScreen(val componentContext: SaveContactComponent): Child()
        data class EditContactScreen(val componentContext: EditContactComponent): Child()
        data class ContactsListScreen(val componentContext: ContactsListComponent): Child()
    }
}