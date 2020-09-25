package com.example.PassMan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.example.PassMan.R
import com.example.PassMan.utils.ReusableTasks
import com.example.PassMan.utils.SessionManager
import com.example.PassMan.utils.showToast
import kotlinx.android.synthetic.main.activity_change_password.*

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

        when (performTask.canProceed(currPass)){
            true -> {
                when (performTask.canProceed(newPass)) {
                    true -> {
                        when (session.proceedAfterCheck(currPass)) {
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

        val pb_loading: ProgressBar = findViewById(R.id.pbLoading)

        pb_loading.visibility = View.VISIBLE
        btnChangePass.visibility = View.GONE

        if (dataValidation()){
            showToast(getText(R.string.success).toString())
            val intent = Intent(this,ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
        else{
            pb_loading.visibility = View.GONE
            btnChangePass.visibility = View.VISIBLE
        }

    }
}