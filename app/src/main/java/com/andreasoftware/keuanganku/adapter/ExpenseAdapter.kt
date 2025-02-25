package com.andreasoftware.keuanganku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.database.entities.Expense

class ExpenseAdapter(private val expenses: List<Expense>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_expense_item, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int {
        return expenses.size
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val expenseTitle: TextView = itemView.findViewById(R.id.expenseTitle)
        private val expenseAmount: TextView = itemView.findViewById(R.id.expenseAmount)

        fun bind(expense: Expense) {
            expenseTitle.text = expense.title
            expenseAmount.text = "Rp ${expense.amount}"
        }
    }
}