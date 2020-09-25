package com.example.PassMan.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.PassMan.R
import com.example.PassMan.adapters.AccountDetailsAdapter
import com.example.PassMan.models.AccountDetails
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    val accountDetails = ArrayList<AccountDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val accountDetailsAdapter = AccountDetailsAdapter(accountDetails)
        rv_home.adapter = accountDetailsAdapter
        rv_home.setHasFixedSize(true)
    }

    private fun initData() {
        accountDetails.add(
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
        ))
    }
}