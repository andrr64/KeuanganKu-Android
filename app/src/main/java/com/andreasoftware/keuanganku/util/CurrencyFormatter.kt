package com.andreasoftware.keuanganku.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {
    fun formatCurrency(amount: Double, locale: Locale): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }
}
