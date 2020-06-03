package com.example.conceptsofkotlin.utils

import android.content.Context

class ReusableTasks(context: Context) {

    //Lambda function to check if string is null
    var isNullLambda: (String) -> Boolean = {str -> str.isEmpty()}

    //Higher order function for further action after string check
    fun canProceed(string: String, lamFunct: (String) -> Boolean): Boolean{
        return !lamFunct(string)
        }
}