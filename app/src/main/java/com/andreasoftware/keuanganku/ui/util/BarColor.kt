package com.andreasoftware.keuanganku.ui.util

import android.graphics.Color

object BarColor {
    fun getColorFromPercentageScale1(percentage: Double): Int {
        // Pastikan persentase berada dalam rentang 0-100
        val clampedPercentage = percentage.coerceIn(0.0, 100.0)

        // Hitung warna merah dan hijau berdasarkan persentase
        val red = (clampedPercentage / 100f * 255).toInt()
        val green = ((100f - clampedPercentage) / 100f * 255).toInt()

        // Gabungkan warna merah dan hijau dengan warna biru (0)
        return Color.rgb(red, green, 0)
    }
}