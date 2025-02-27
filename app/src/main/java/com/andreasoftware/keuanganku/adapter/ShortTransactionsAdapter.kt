package com.andreasoftware.keuanganku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.sqlite.entities.Income
import com.andreasoftware.keuanganku.ui.utils.StringFormatter

class ShortTransactionsAdapter(private val transactions: List<Income>) :
    RecyclerView.Adapter<ShortTransactionsAdapter.ShortTransactionViewHolder>() {

    class ShortTransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_description)
        val amountTextView: TextView = itemView.findViewById(R.id.tv_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortTransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_transactions_item, parent, false)
        return ShortTransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShortTransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.titleTextView.text = transaction.title
        holder.descriptionTextView.text = "Dummyyy"
        holder.amountTextView.text = StringFormatter.formatNumber(transaction.amount)
    }

    override fun getItemCount(): Int = transactions.size
}