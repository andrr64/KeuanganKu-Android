package com.andreasoftware.keuanganku.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.util.CurrencyFormatter
import java.util.Locale

class TransactionItemAdapter(
    private var transactions: List<TransactionModel>,
    private val categoryRepository: CategoryRepository,
    private val locale: Locale,
    private val onItemClick: (TransactionModel) -> Unit
) : RecyclerView.Adapter<TransactionItemAdapter.TransactionItemViewHolder>() {

    class TransactionItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.transactionTitleTextview)
        val amount: TextView = itemView.findViewById(R.id.transactionAmountTextview)
        val category: TextView = itemView.findViewById(R.id.transactonCategoryNameTextview)
        val type: TextView = itemView.findViewById(R.id.transactionTypeTextview)
        val transactionTypeIc: ImageView = itemView.findViewById(R.id.transactionItemIconType)
        val parent: CardView = itemView.findViewById(R.id.componentTransactionItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_transaction_item, parent, false)
        return TransactionItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionItemViewHolder, position: Int) {
        val transaction = transactions[position]
        val category = categoryRepository.getCategoryById(transaction.categoryId)

        holder.title.text = transaction.title
        holder.amount.text = CurrencyFormatter.formatCurrency(transaction.amount, locale)
        holder.category.text = category?.name ?: "Unknown"
        holder.type.text = TransactionType.getDisplayName(transaction.transactionTypeId)
        holder.parent.setOnClickListener { onItemClick(transaction) }

        if (transaction.transactionTypeId == TransactionType.INCOME.value) {
            holder.transactionTypeIc.setImageResource(R.drawable.ic_income_type_24)
        } else {
            holder.transactionTypeIc.setImageResource(R.drawable.ic_expense_type_24)
        }
    }

    override fun getItemCount(): Int = transactions.size

    fun updateTransactions(newTransactions: List<TransactionModel>) {
        transactions = newTransactions
        notifyDataSetChanged()
    }
}
