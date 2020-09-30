package com.example.PassMan.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

data class PassManEntry(
    @PrimaryKey
    val id : String = UUID.randomUUID().toString(),
    var title : String,
    var email : String,
    var password : String,
    val date : Long = Date().time
) : RealmObject()