package com.andreasoftware.keuanganku.util

import android.icu.text.DecimalFormat

object NumericFormater {
    fun toPercentage(value: Double): String {
        val decimalFormat = DecimalFormat("#.##") // Format to 2 decimal places
        return "${decimalFormat.format(value)}%"
    }
}