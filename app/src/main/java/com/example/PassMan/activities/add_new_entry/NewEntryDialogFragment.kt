package com.example.PassMan.activities.add_new_entry

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.PassMan.R
import com.example.PassMan.models.NewAccountEntry
import com.example.PassMan.utils.ReusableTasks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_new_entry_dialog.*

class NewEntryDialogFragment : BottomSheetDialogFragment() {

    private val realmInstance = Realm.getDefaultInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_new_entry_dialog,
            container, false
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBtn.setOnClickListener {
            valiDateInputs()
        }
    }

    private fun valiDateInputs() {
        val title = titleTL?.editText?.text?.toString() ?: ""
        val email = emailTL?.editText?.text?.toString() ?: ""
        val password = passwordTL?.editText?.text?.toString() ?: ""
        titleTL.error = ""
        emailTL.error = ""
        passwordTL.error = ""
        val performTask = ReusableTasks(requireContext())
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
        dismiss()
    }

}