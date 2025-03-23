package com.andreasoftware.keuanganku.util

import android.icu.text.DecimalFormat

object NumericFormater {
    fun toPercentage(value: Double): String {
        if (value <= 0){
            return "0.00%"
        }
        val decimalFormat = DecimalFormat("#.##") // Format to 2 decimal places
        return "${decimalFormat.format(value)}%"
    }

    fun from100to1ScaleNotZero(value: Double): Double {
        if (value <= 0) return 0.0
        if (value >= 1) return 1.0
        return value/100.0
    }

    fun to100ScaleFromAperBNotZero(val1: Double, val2: Double): Double{
        if (val1 <= 0) return 0.0
        return val1/val2 * 100.0
    }
}