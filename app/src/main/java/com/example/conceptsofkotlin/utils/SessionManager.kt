package com.example.conceptsofkotlin.utils

import android.content.Context
import java.lang.ref.PhantomReference

class SessionManager(context : Context) {

    val PREF_NAME = "LOGIN"
    val LOGIN = "IS_LOGIN"
    val PASSWORD = "PASS"

    val sharedPref = context.getSharedPreferences(PREF_NAME,0)
    val editor = sharedPref.edit()

}