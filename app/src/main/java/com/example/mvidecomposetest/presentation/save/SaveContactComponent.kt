package com.example.mvidecomposetest.presentation.save

import android.os.Parcelable
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

interface SaveContactComponent {
    val state: StateFlow<Model>

    fun onUpdateUserName(userName: String)
    fun onUpdateMobilePhone(mobilePhone: String)
    fun onSave()

    @Parcelize
    data class Model(val userName: String, val mobilePhone: String): Parcelable
}
