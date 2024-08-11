package com.example.mvidecomposetest.domain

import android.os.Parcelable
import com.example.mvidecomposetest.byDefault
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int = Int.byDefault,
    val username: String,
    val mobilePhone: String
): Parcelable
