package com.example.mvidecomposetest.presentation.edit

import android.os.Parcelable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface EditContactComponent {
    val state: StateFlow<Model>

    fun onUpdateUserName(userName: String)
    fun onUpdateMobilePhone(mobilePhone: String)
    fun onSave()

    @Parcelize
    data class Model(val id: Int, val userName: String, val mobilePhone: String): Parcelable
}
