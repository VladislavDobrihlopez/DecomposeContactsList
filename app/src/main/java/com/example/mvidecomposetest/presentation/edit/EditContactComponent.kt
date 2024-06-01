package com.example.mvidecomposetest.presentation.edit

import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {
    val state: StateFlow<Model>

    fun onUpdateUserName(userName: String)
    fun onUpdateMobilePhone(mobilePhone: String)
    fun onSave(model: Model)

    data class Model(val userName: String, val mobilePhone: String)
}
