package com.example.PassMan.utils

import android.content.Context

class SessionManager(context : Context) {

    val PREF_NAME = "LOGIN"
    val PASSWORD = "PASS"

    val sharedPref = context.getSharedPreferences(PREF_NAME,0)
    val editor = sharedPref.edit()

    //Function to save password
    fun saveCurrentPass(pass: String) {
        editor.putString(PASSWORD,pass)
        editor.apply()
    }

    //lambda to check if password matches
    var checkPasses: (String) -> Boolean = {enteredPass -> enteredPass == sharedPref.getString(PASSWORD,"admin@123") }

    //higher order function to proceed after password check
    fun proceedAfterCheck(enteredPass: String, comparision: (String) -> Boolean = checkPasses): Boolean {
        return comparision(enteredPass)
        }
}