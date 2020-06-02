package com.example.conceptsofkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.conceptsofkotlin.R

class ActivityLogin : AppCompatActivity() {

    lateinit var ed_password: EditText
    lateinit var pb_loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //mapping view objects
        ed_password = findViewById(R.id.edPassword)
        pb_loading = findViewById(R.id.pbLoading)

        val btn_login: Button = findViewById(R.id.btnLogin)
        val tv_changePassword: TextView = findViewById(R.id.tv_changePass)

        //actions on clicking
        tv_changePassword.setOnClickListener { changePass() }
        btn_login.setOnClickListener { canProceed(ed_password.text.toString().trim(),isNullLambda) }

    }

    //Lambda function to check if string is null
    var isNullLambda: (String) -> Boolean = {str -> str.isEmpty()}

    //Higher order function for further action after string check
    fun canProceed(string: String, lamFunct: (String) -> Boolean){
        when (lamFunct(string)){
            true -> ed_password.setError("Empty Field !")
            else -> onLogin(string)
        }
    }

    //when
    private fun onLogin(pass: String) {
        Toast.makeText(this,pass,Toast.LENGTH_LONG).show()
    }

    //Directing to activity for changing password
    private fun changePass() {
        val intent = Intent(this,ActivityChangePassword::class.java)
        startActivity(intent)
        finish()
    }
}
