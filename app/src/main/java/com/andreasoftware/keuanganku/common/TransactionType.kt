package com.andreasoftware.keuanganku.common

enum class TransactionType(val value: Int ) {
    INCOME(1),
    EXPENSE(2);

    companion object  {
        fun getDisplayName(value: Int): String {
            return when (value) {
                INCOME.value -> "Income"
                EXPENSE.value -> "Expense"
                else -> "Unknown"
            }
        }
    }
}