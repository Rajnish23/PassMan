package com.example.conceptsofkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.conceptsofkotlin.R
import com.example.conceptsofkotlin.utils.ReusableTasks
import com.example.conceptsofkotlin.utils.SessionManager

class ActivityChangePassword : AppCompatActivity() {

    lateinit var ed_currentPass: EditText
    lateinit var ed_newPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        ed_currentPass = findViewById(R.id.edCurrentPass)
        ed_newPass = findViewById(R.id.edNewPassword)
        val btn_changePass: Button = findViewById(R.id.btnChangePass)

        btn_changePass.setOnClickListener { onChangePassword() }

    }

    private fun dataValidation(): Boolean{
        val performTask = ReusableTasks(this)
        val session = SessionManager(this)
        val currPass: String=ed_currentPass.text.toString().trim()
        val newPass: String = ed_newPass.text.toString().trim()

        when (performTask.canProceed(currPass,performTask.isNullLambda)){
            true -> {
                when (performTask.canProceed(newPass, performTask.isNullLambda)) {
                    true -> {
                        when (session.proceedAfterCheck(currPass, session.checkPasses)) {
                            true -> {
                                session.saveCurrentPass(newPass)
                                return true
                            }
                            else -> ed_currentPass.error = getText(R.string.incorrect_value)
                        }
                    }
                    else -> ed_newPass.error = getText(R.string.empty_field)
                }
            }

            else -> ed_currentPass.error = getText(R.string.empty_field)

        }
        return false

    }

    private fun onChangePassword(){

        if (dataValidation()){

            Toast.makeText(this,getText(R.string.success),Toast.LENGTH_LONG).show()
            val intent = Intent(this,ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }

    }
}