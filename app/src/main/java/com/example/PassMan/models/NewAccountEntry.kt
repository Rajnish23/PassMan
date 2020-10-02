package com.example.PassMan.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class NewAccountEntry(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var email: String = "",
    var password: String = "",
    var createdDate: Long = Date().time
) : RealmObject() {

}