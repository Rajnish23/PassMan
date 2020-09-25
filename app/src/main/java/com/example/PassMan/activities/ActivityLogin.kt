package com.example.PassMan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.PassMan.R
import com.example.PassMan.utils.ReusableTasks
import com.example.PassMan.utils.SessionManager
import com.example.PassMan.utils.showToast
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity() {

    lateinit var ed_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //mapping view objects
        ed_password = findViewById(R.id.edPassword)

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

        when (performTask.canProceed(enteredPass)){
            true -> {
                when (session.proceedAfterCheck(enteredPass)){
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

        val pb_loading: ProgressBar = findViewById(R.id.pbLoading)

        pb_loading.visibility = View.VISIBLE
        btnLogin.visibility = View.GONE

        if(dataValidation()){
            showToast(getText(R.string.success).toString())
            val intent = Intent(this,ActivityHome::class.java)
            startActivity(intent)
            finish()
        }
        else{
            pb_loading.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }
    }

    //Directing to activity for changing password
    private fun changePass() {
        val intent = Intent(this,ActivityChangePassword::class.java)
        startActivity(intent)
        finish()
    }
}
