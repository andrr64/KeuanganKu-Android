package com.andreasoftware.keuanganku.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.TimePeriod
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.data.repository.ExpenseLimiterRepository
import com.andreasoftware.keuanganku.ui.util.BarColor
import com.andreasoftware.keuanganku.util.CurrencyFormatter
import com.andreasoftware.keuanganku.util.NumericFormater
import java.util.Locale

class ExpenseLimiterItemAdapter(
    private var expenseLimiters: List<ExpenseLimiterModel>,
    private val categoryRepository: CategoryRepository,
    private val locale: Locale,
    private val onItemClick: (ExpenseLimiterModel) -> Unit
): RecyclerView.Adapter<ExpenseLimiterItemAdapter.ExpenseLimiterItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenseLimiterItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.component_expense_limiter_item, parent, false)
        return ExpenseLimiterItemViewHolder(layout)
    }

    override fun onBindViewHolder(
        holder: ExpenseLimiterItemViewHolder,
        position: Int
    ) {
        ///TODO: handle
        val data = expenseLimiters[position]
        val category = categoryRepository.getCategoryById(data.categoryId)
        val budgetLimitAmount = data.limitAmount
        val spentAmount = 60000.0
        var spendingPercentage = NumericFormater.to100ScaleFromAperBNotZero(spentAmount, budgetLimitAmount)

        holder.title.text = TimePeriod.getDisplayNameByValue(data.enumTimePeriodValue?: 0)
        holder.category.text = category?.name ?: "Unknown"
        holder.spentAmount.text = CurrencyFormatter.formatCurrency(spentAmount, locale)

        holder.spendingPercentage.text = NumericFormater.toPercentage(spendingPercentage)
        holder.budgetLimit.text = CurrencyFormatter.formatCurrency(data.limitAmount, locale)
        holder.parent.setOnClickListener { onItemClick(data) }

        val params = holder.percentageBar.layoutParams as ConstraintLayout.LayoutParams
        params.matchConstraintPercentWidth = (if (spendingPercentage > 0 && spendingPercentage <= 100) (spendingPercentage/100) else (
            if (spendingPercentage > 100) 1 else 0
        )  ) .toFloat()
        holder.percentageBar.layoutParams = params
        holder.percentageBar.setBackgroundColor(BarColor.getColorFromPercentageScale1(spendingPercentage))
    }

    override fun getItemCount(): Int = expenseLimiters.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateExpenseLimiters(expenseLimiters: List<ExpenseLimiterModel>) {
        this.expenseLimiters = expenseLimiters
        notifyDataSetChanged()
    }

    class ExpenseLimiterItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tv_date_range)
        val category: TextView = itemView.findViewById(R.id.tv_category)
        val spentAmount: TextView = itemView.findViewById(R.id.tv_spent_amount)
        val spendingPercentage: TextView = itemView.findViewById(R.id.tv_spending_percentage)
        val budgetLimit: TextView = itemView.findViewById(R.id.tv_budget_limit)
        val parent: CardView = itemView.findViewById(R.id.layout_expenselimiter_item)
        val percentageBar: View = itemView.findViewById(R.id.bar_percentage)
    }
}