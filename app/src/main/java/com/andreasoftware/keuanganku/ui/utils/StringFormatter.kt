package com.andreasoftware.keuanganku.ui.utils

import java.text.NumberFormat
import java.util.Locale

class StringFormatter {

    ///TODO: Add localization
    companion object {
        fun formatNumber(value: Double, locale: Locale = Locale("id", "ID")): String {
            val formatter = NumberFormat.getCurrencyInstance(locale)
            return formatter.format(value)
        }
    }
}