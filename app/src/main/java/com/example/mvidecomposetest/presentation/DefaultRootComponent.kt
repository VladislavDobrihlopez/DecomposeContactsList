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

    override val state: Value<ChildStack<Config, RootComponent.Child>> = childStack(
        source = backStack,
        initialConfiguration = Config.ContactsListScreenConfig,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            Config.AddContactScreenConfig -> {
                RootComponent.Child.AddContactScreen(
                    DefaultSaveContactComponent(
                        componentContext = componentContext,
                        onSaveSuccessfully = {
                            backStack.pop()
                        })
                )
            }

            Config.ContactsListScreenConfig -> {
                RootComponent.Child.ContactsListScreen(
                    DefaultContactsListComponent(
                        componentContext = componentContext,
                        onAddContactRequested = {
                            backStack.push(Config.AddContactScreenConfig)
                        }, onEditingContactRequested = { contact ->
                            backStack.push(Config.EditContactScreenConfig(contact))
                        })
                )
            }

            is Config.EditContactScreenConfig -> {
                RootComponent.Child.EditContactScreen(
                    DefaultEditContactComponent(
                        componentContext = componentContext,
                        contact = config.contact,
                        onSaveSuccessfully = {
                            backStack.pop()
                        }
                    ))
            }
        }
    }

    @Parcelize
    sealed interface Config : Parcelable {
        @Parcelize
        object AddContactScreenConfig : Config

        @Parcelize
        object ContactsListScreenConfig : Config

        @Parcelize
        data class EditContactScreenConfig(val contact: Contact) : Config
    }
}