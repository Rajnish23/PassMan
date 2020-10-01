package com.example.PassMan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        /* accountDetails.add(
             AccountDetails(

             "Gmail","gmail@gmail.com","gmail@123"
         )
         )

         accountDetails.add(AccountDetails(

             "YouTube","youtube@gmail.com","youtube@123"
         ))

         accountDetails.add(AccountDetails(

             "GitHub","github@gmail.com","github@123"
         ))

         accountDetails.add(AccountDetails(

             "BitBucket","bitbucket@gmail.com","bitbucket@123"
         ))*/
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