package com.example.PassMan.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.PassMan.PassManApplication
import com.example.PassMan.R
import com.example.PassMan.adapters.AccountDetailsAdapter
import com.example.PassMan.models.AccountDetails
import com.example.PassMan.models.NewAccountEntry
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    companion object{
        val TAG = PassManApplication::class.simpleName
    }
    val accountDetailList = mutableListOf<AccountDetails>()
    private val accountDetailsAdapter = AccountDetailsAdapter(accountDetailList)
    private val realmInstance = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initData()
        setRecyclerView()

        addNewEntryBtn.setOnClickListener {
           navigateToNewEntryActivity()
        }
    }

    private fun navigateToNewEntryActivity() {
        val intent = Intent(this, ActivityNewEntry::class.java)
        startActivity(intent)
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
        for (accountEntry in allAccountEntry){
            val accountDetails = AccountDetails(
                accountName = accountEntry.title,
                email = accountEntry.email,
                password = accountEntry.password
            )
            accountDetailList.add(accountDetails)
        }
        accountDetailsAdapter.notifyDataSetChanged()

    }
}