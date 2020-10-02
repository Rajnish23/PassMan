package com.example.PassMan.models

//This is a data class for the elements of recycler view

data class AccountDetails(
    val uuid: String,
    val accountName: String,
    val email: String,
    val password: String,
    var expandable: Boolean = false
)