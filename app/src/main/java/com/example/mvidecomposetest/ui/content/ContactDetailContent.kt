package com.example.mvidecomposetest.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mvidecomposetest.presentation.edit.EditContactComponent
import com.example.mvidecomposetest.presentation.save.SaveContactComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    component: SaveContactComponent,
) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.userName,
            placeholder = {
                Text(text = "Username:")
            },
            onValueChange = component::onUpdateUserName
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.mobilePhone,
            placeholder = {
                Text(text = "Phone:")
            },
            onValueChange = component::onUpdateMobilePhone
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.onSave()
            }
        ) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactScreen(
    component: EditContactComponent,
) {
    val state by component.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.userName,
            placeholder = {
                Text(text = "Username:")
            },
            onValueChange = component::onUpdateUserName
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.mobilePhone,
            placeholder = {
                Text(text = "Phone:")
            },
            onValueChange = component::onUpdateMobilePhone
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                component.onSave()
            }
        ) {
            Text(text = "Update")
        }
    }
}
