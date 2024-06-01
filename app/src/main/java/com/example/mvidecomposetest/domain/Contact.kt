package com.example.mvidecomposetest.domain

import com.example.mvidecomposetest.byDefault

data class Contact(
    val id: Int = Int.byDefault,
    val userName: String,
    val mobilePhone: String
)
