package com.example.PassMan.activities.add_new_entry

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.PassMan.R
import com.example.PassMan.models.NewAccountEntry
import com.example.PassMan.utils.ReusableTasks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_new_entry_dialog.*
import java.util.*

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
        val id: String = arguments?.getString("id", "") ?: ""

        if (id.isNotEmpty()) {
            saveBtn.text = getString(R.string.update)
            fetchData(id)
        }
        saveBtn.setOnClickListener {
            val title = titleTL?.editText?.text?.toString() ?: ""
            val email = emailTL?.editText?.text?.toString() ?: ""
            val password = passwordTL?.editText?.text?.toString() ?: ""
            titleTL.error = ""
            emailTL.error = ""
            passwordTL.error = ""
            if (valiDateInputs(title, email, password)) {
                if (id.isEmpty()) {
                    addEntryToDatabase(
                        uuid = UUID.randomUUID().toString(),
                        title = title,
                        email = email,
                        password = password
                    )
                } else {
                    addEntryToDatabase(
                        uuid = id,
                        title = title,
                        email = email,
                        password = password
                    )
                }
            }
        }
    }

    private fun fetchData(id: String) {
        realmInstance.beginTransaction()
        val entry = realmInstance.where(NewAccountEntry::class.java).equalTo("id", id).findFirst()
        entry?.let {
            titleTL.editText?.setText(it.title)
            emailTL.editText?.setText(it.email)
            passwordTL.editText?.setText(it.password)
        }
        realmInstance.commitTransaction()
    }

    private fun valiDateInputs(title: String, email: String, password: String): Boolean {
        val performTask = ReusableTasks(requireContext())
        when (performTask.canProceed(title)) {
            true -> {

            }
            else -> {
                titleTL.error = "Required"
                return false
            }
        }
        when (performTask.canProceed(email)) {
            true -> {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailTL.error = "Invalid Email"
                    return false
                }
            }
            else -> {
                emailTL.error = "Required"
                return false
            }
        }
        when (performTask.canProceed(password)) {
            true -> {
                return true
            }
            else -> {
                passwordTL.error = "Required"
                return false
            }
        }
    }

    private fun addEntryToDatabase(uuid: String, title: String, email: String, password: String) {
        realmInstance.beginTransaction()
        val newAccountEntry = NewAccountEntry(
            id = uuid,
            title = title,
            email = email,
            password = password
        )
        realmInstance.insertOrUpdate(newAccountEntry)
        realmInstance.commitTransaction()
        dismiss()
    }

}