package com.example.mvidecomposetest.presentation.save

import kotlinx.coroutines.flow.StateFlow

interface SaveContactComponent {
    val state: StateFlow<Model>

    fun onUpdateUserName(userName: String)
    fun onUpdateMobilePhone(mobilePhone: String)
    fun onSave(model: Model)

    data class Model(val userName: String, val mobilePhone: String)
}
