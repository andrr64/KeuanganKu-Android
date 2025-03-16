package com.andreasoftware.keuanganku.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.enm.TransactionType
import com.andreasoftware.keuanganku.data.model.TransactionModel

class TransactionItemAdapter(private var transactions: List<TransactionModel>):
RecyclerView.Adapter<TransactionItemAdapter.TransactionItemViewHolder>()
{
    class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.transactionTitleTextview)
        val amount: TextView = itemView.findViewById(R.id.transactionAmountTextview)
        val category: TextView = itemView.findViewById(R.id.transactonCategoryNameTextview)
        val type: TextView = itemView.findViewById(R.id.transactionTypeTextview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.component_transaction_item, parent, false)
        return TransactionItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TransactionItemViewHolder,
        position: Int
    ) {
        val transaction = transactions[position]
        holder.title.text = transaction.title
        holder.amount.text = transaction.amount.toString()
        holder.category.text = transaction.categoryId.toString()
        holder.type.text = TransactionType.getDisplayName(transaction.transactionTypeId)
    }

    override fun getItemCount(): Int = transactions.size

    fun updateTransactions(newTransactions: List<TransactionModel>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}