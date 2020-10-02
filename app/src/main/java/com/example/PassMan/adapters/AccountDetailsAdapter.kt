package com.example.PassMan.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.example.PassMan.R
import com.example.PassMan.models.AccountDetails
import java.lang.IllegalStateException

class AccountDetailsAdapter(val detailList: List<AccountDetails>, val listener : AccountClickListener): RecyclerView.Adapter<AccountDetailsAdapter.AccountDetailsVH>(){
    class AccountDetailsVH(itemView: View): RecyclerView.ViewHolder(itemView) {

        var tv_account: TextView = itemView.findViewById(R.id.tv_account)
        var tv_email: TextView = itemView.findViewById(R.id.tv_email)
        var tv_pass: TextView = itemView.findViewById(R.id.tv_password)

        var ll_outerLayout: ConstraintLayout = itemView.findViewById(R.id.ll_element)
        var ll_innerLayout: Group = itemView.findViewById(R.id.ll_expandable)
        var more_option_btn : ImageButton = itemView.findViewById(R.id.more_option_btn)

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

        holder.more_option_btn.setOnClickListener {
            val popupMenu = PopupMenu(holder.more_option_btn.context, holder.more_option_btn)
            popupMenu.inflate(R.menu.overflow_menu)

            popupMenu.setOnMenuItemClickListener {menuItem ->
                when(menuItem.itemId){
                    R.id.menu_edit -> {
                        listener.onEdit(accountDetails.uuid)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.menu_delete -> {
                        listener.onDelete(accountDetails.uuid)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        throw IllegalStateException("Unknown Menu Item Click ${menuItem.itemId}")
                    }
                }
            }

            popupMenu.show()
        }
    }

    interface AccountClickListener{
        fun onEdit(uuid : String)
        fun onDelete(uuid : String)
    }
}