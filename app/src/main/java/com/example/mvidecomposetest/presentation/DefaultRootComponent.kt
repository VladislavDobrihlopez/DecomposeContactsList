package com.example.mvidecomposetest.presentation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.presentation.edit.DefaultEditContactComponent
import com.example.mvidecomposetest.presentation.list.DefaultContactsListComponent
import com.example.mvidecomposetest.presentation.save.DefaultSaveContactComponent
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val backStack = StackNavigation<Config>()

    val state: Value<ChildStack<Config, ComponentContext>> = childStack(
        source = backStack,
        initialConfiguration = Config.ContactsListScreenConfig,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, componentContext: ComponentContext): ComponentContext {
        return when (config) {
            Config.AddContactScreenConfig -> {
                DefaultSaveContactComponent(
                    componentContext = componentContext,
                    onSaveSuccessfully = {
                        backStack.pop()
                    })
            }

            Config.ContactsListScreenConfig -> {
                DefaultContactsListComponent(
                    componentContext = componentContext,
                    onAddContactRequested = {
                        backStack.push(Config.AddContactScreenConfig)
                    }, onEditingContactRequested = { contact ->
                        backStack.push(Config.EditContactScreenConfig(contact))
                    })
            }

            is Config.EditContactScreenConfig -> {
                DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = config.contact,
                    onSaveSuccessfully = {
                        backStack.pop()
                    }
                )
            }
        }
    }

    @Parcelize
    sealed interface Config : Parcelable {
        object AddContactScreenConfig : Config
        object ContactsListScreenConfig : Config
        data class EditContactScreenConfig(val contact: Contact) : Config
    }
}