package com.example.mvidecomposetest.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.mvidecomposetest.presentation.RootComponent
import com.example.mvidecomposetest.ui.theme.MviDecomposeTestTheme

@Composable
fun RootContent(rootComponent: RootComponent) {
    MviDecomposeTestTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Children(
                stack = rootComponent.state,
                animation = stackAnimation()
            ) { configData ->
                when (val instance = configData.instance) {
                    is RootComponent.Child.AddContactScreen -> {
                        AddContactScreen(component = instance.componentContext)
                    }
                    is RootComponent.Child.ContactsListScreen -> {
                        ContactsScreen(component = instance.componentContext)
                    }
                    is RootComponent.Child.EditContactScreen -> {
                        EditContactScreen(component = instance.componentContext)
                    }
                }
            }
        }
    }
}