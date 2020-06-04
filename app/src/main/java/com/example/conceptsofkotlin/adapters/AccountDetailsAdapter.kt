package com.example.conceptsofkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.conceptsofkotlin.R
import com.example.conceptsofkotlin.models.AccountDetails

class AccountDetailsAdapter(val detailList: List<AccountDetails>): RecyclerView.Adapter<AccountDetailsAdapter.AccountDetailsVH>(){
    class AccountDetailsVH(itemView: View): RecyclerView.ViewHolder(itemView) {

        var tv_account: TextView = itemView.findViewById(R.id.tv_account)
        var tv_email: TextView = itemView.findViewById(R.id.tv_email)
        var tv_pass: TextView = itemView.findViewById(R.id.tv_password)

        var ll_outerLayout: LinearLayout = itemView.findViewById(R.id.ll_element)
        var ll_innerLayout: LinearLayout = itemView.findViewById(R.id.ll_expandable)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountDetailsVH {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_elements,parent,false)
        return AccountDetailsVH(view)
    }

    override fun getItemCount(): Int {

        return  detailList.size
    }

    override fun onBindViewHolder(holder: AccountDetailsVH, position: Int) {

        val accountDetails: AccountDetails = detailList[position]
        holder.tv_account.text = accountDetails.accountName
        holder.tv_email.text = accountDetails.email
        holder.tv_pass.text = accountDetails.password

        val isExpandable: Boolean = detailList[position].expandable
        holder.ll_innerLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.ll_outerLayout.setOnClickListener {
            val accountDetails = detailList[position]
            accountDetails.expandable = !accountDetails.expandable
            notifyItemChanged(position)
        }
    }
}