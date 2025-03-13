package com.andreasoftware.keuanganku.common.util

import com.andreasoftware.keuanganku.common.enm.TimePeriod
import java.util.Calendar
import java.util.Date

fun getLongTimeByPeriod(
    period: TimePeriod,
    startDate: Date? = null,
    endDate: Date? = null
): Pair<Long, Long> {
    val calendar = Calendar.getInstance()
    return when (period) {
        TimePeriod.WEEK -> {
            calendar.time = startDate
                ?: Date() // Gunakan startDate jika disediakan, jika tidak, gunakan waktu saat ini
            calendar.firstDayOfWeek = Calendar.MONDAY
            calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            val start = calendar.timeInMillis

            calendar.time = endDate
                ?: Date() // Gunakan endDate jika disediakan, jika tidak, gunakan waktu saat ini
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
            calendar.add(Calendar.MILLISECOND, -1)
            val end = calendar.timeInMillis

            Pair(start, end)
        }

        TimePeriod.MONTH -> {
            calendar.time = startDate ?: Date()
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            val start = calendar.timeInMillis

            calendar.time = endDate ?: Date()
            calendar.add(Calendar.MONTH, 1)
            calendar.add(Calendar.MILLISECOND, -1)
            val end = calendar.timeInMillis

            Pair(start, end)
        }

        TimePeriod.YEAR -> {
            calendar.time = startDate ?: Date()
            calendar.set(Calendar.DAY_OF_YEAR, 1)
            val start = calendar.timeInMillis

            calendar.time = endDate ?: Date()
            calendar.add(Calendar.YEAR, 1)
            calendar.add(Calendar.MILLISECOND, -1)
            val end = calendar.timeInMillis

            Pair(start, end)
        }
    }
}