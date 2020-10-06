package com.example.PassMan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.PassMan.R
import com.example.PassMan.models.NewAccountEntry
import com.example.PassMan.utils.ReusableTasks
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_new_entry.emailTL
import kotlinx.android.synthetic.main.activity_new_entry.passwordTL
import kotlinx.android.synthetic.main.activity_new_entry.saveBtn
import kotlinx.android.synthetic.main.activity_new_entry.titleTL

class ActivityNewEntry : AppCompatActivity() {

    private val realmInstance = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_entry)

        saveBtn.setOnClickListener {
            onSave()
        }
    }

    private fun onSave() {
        val title = titleTL?.editText?.text?.toString() ?: ""
        val email = emailTL?.editText?.text?.toString() ?: ""
        val password = passwordTL?.editText?.text?.toString() ?: ""
        titleTL.error = ""
        emailTL.error = ""
        passwordTL.error = ""
        val performTask = ReusableTasks(this)
        when (performTask.canProceed(title)) {
            true -> {

            }
            else -> {
                titleTL.error = "Required"
                return
            }
        }
        when (performTask.canProceed(email)) {
            true -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTL.error = "Invalid Email"
                    return
                }
            }
            else -> {
                emailTL.error = "Required"
                return
            }
        }
        when (performTask.canProceed(password)) {
            true -> {
                addEntryToDatabase(title, email, password)
            }
            else -> {
                passwordTL.error = "Required"
                return
            }
        }
    }

    private fun addEntryToDatabase(title: String, email: String, password: String) {
        realmInstance.beginTransaction()
        val newAccountEntry = NewAccountEntry(
            title = title,
            email = email,
            password = password
        )
        realmInstance.insert(newAccountEntry)
        realmInstance.commitTransaction()
        finish()
    }
}