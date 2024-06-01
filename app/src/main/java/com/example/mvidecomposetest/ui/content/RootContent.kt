package com.example.mvidecomposetest.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.mvidecomposetest.presentation.DefaultRootComponent
import com.example.mvidecomposetest.presentation.edit.EditContactComponent
import com.example.mvidecomposetest.presentation.list.ContactsListComponent
import com.example.mvidecomposetest.presentation.save.SaveContactComponent
import com.example.mvidecomposetest.ui.theme.MviDecomposeTestTheme

@Composable
fun RootContent(rootComponent: DefaultRootComponent) {
    MviDecomposeTestTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Children(
                stack = rootComponent.state,
                animation = stackAnimation()
            ) { configData ->
                when (val instance = configData.instance) {
                    is EditContactComponent -> {
                        EditContactScreen(component = instance)
                    }

                    is SaveContactComponent -> {
                        AddContactScreen(component = instance)
                    }

                    is ContactsListComponent -> {
                        ContactsScreen(component = instance)
                    }
                }
            }
        }
    }
}