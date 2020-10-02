package com.example.PassMan.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.PassMan.PassManApplication
import com.example.PassMan.R
import com.example.PassMan.activities.add_new_entry.NewEntryDialogFragment
import com.example.PassMan.adapters.AccountDetailsAdapter
import com.example.PassMan.models.AccountDetails
import com.example.PassMan.models.NewAccountEntry
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    companion object {
        val TAG = PassManApplication::class.simpleName
    }

    val accountDetailList = mutableListOf<AccountDetails>()
    private val accountDetailsAdapter = AccountDetailsAdapter(
        accountDetailList,
        object : AccountDetailsAdapter.AccountClickListener {
            override fun onEdit(uuid: String) {
                val dialog = NewEntryDialogFragment()
                dialog.arguments = Bundle().apply {
                    putString("id", uuid)
                }
                dialog.show(supportFragmentManager, "entry_dialog")
            }

            override fun onDelete(uuid: String) {
                val alertBuilder = AlertDialog.Builder(this@ActivityHome)
                alertBuilder.setTitle("Delete")
                alertBuilder.setMessage("Are you sure?")
                alertBuilder.setPositiveButton(
                    getString(R.string.yes)
                ) { p0, p1 ->
                    deleteEntry(uuid)
                    p0.dismiss()
                }

                alertBuilder.setNegativeButton(
                    getString(R.string.cancel)
                ) { p0, p1 ->
                    p0.dismiss()
                }
                val dialog = alertBuilder.create()
                dialog.show()
            }

        })

    private fun deleteEntry(uuid: String) {
        realmInstance.beginTransaction()
        val entry = realmInstance.where(NewAccountEntry::class.java).equalTo("id", uuid).findFirst()
        entry?.deleteFromRealm()
        realmInstance.commitTransaction()
    }

    private val realmInstance = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initData()
        setRecyclerView()

        addNewEntryBtn.setOnClickListener {
            val dialog = NewEntryDialogFragment()
            dialog.show(supportFragmentManager, "entry_dialog")
        }
    }

    private fun setRecyclerView() {
        rv_home.adapter = accountDetailsAdapter
        rv_home.setHasFixedSize(true)
    }

    private fun initData() {
        val allAccountEntry = realmInstance.where(NewAccountEntry::class.java).findAll()
        mapToAccountDetails(allAccountEntry)
        allAccountEntry.addChangeListener { result, changeSet ->
            mapToAccountDetails(result)
        }
    }

    private fun mapToAccountDetails(allAccountEntry: RealmResults<NewAccountEntry>) {
        accountDetailList.clear()
        for (accountEntry in allAccountEntry) {
            val accountDetails = AccountDetails(
                uuid = accountEntry.id,
                accountName = accountEntry.title,
                email = accountEntry.email,
                password = accountEntry.password
            )
            accountDetailList.add(accountDetails)
        }
        accountDetailsAdapter.notifyDataSetChanged()

    }
}