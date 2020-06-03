package com.example.conceptsofkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.conceptsofkotlin.R
import com.example.conceptsofkotlin.utils.ReusableTasks
import com.example.conceptsofkotlin.utils.SessionManager

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
        btn_login.setOnClickListener { onLogin() }

    }

    private fun dataValidation(): Boolean{
        val performTask = ReusableTasks(this)
        val session = SessionManager(this)
        val enteredPass = ed_password.text.toString().trim()

        when (performTask.canProceed(enteredPass,performTask.isNullLambda)){
            true -> {
                when (session.proceedAfterCheck(enteredPass,session.checkPasses)){
                    true -> { return true }
                    else -> ed_password.error = getText(R.string.incorrect_value)
                }
            }
            else -> ed_password.error = getText(R.string.empty_field)
        }

        return false
    }

    //when login button is clicked
    private fun onLogin() {

        if(dataValidation()){
            Toast.makeText(this,getText(R.string.success),Toast.LENGTH_LONG).show()
            val intent = Intent(this,ActivityHome::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Directing to activity for changing password
    private fun changePass() {
        val intent = Intent(this,ActivityChangePassword::class.java)
        startActivity(intent)
        finish()
    }
}
