package com.andreasoftware.keuanganku.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.common.TimePeriod
import java.util.Date
import java.util.Locale

object TimeUtility {
    @SuppressLint("ConstantLocale")
    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US).apply {
        timeZone = TimeZone.getDefault()
    }

    fun getCurrentISO8601(): String {
        return format.format(Date())
    }

    fun getTimePeriodISO8601(timePeriod: TimePeriod): Pair<ISO8601String, ISO8601String> {
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        val start: String
        val end: String

        when (timePeriod) {
            TimePeriod.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                setStartOfDay(calendar)
                start = format.format(calendar.time)

                calendar.add(Calendar.WEEK_OF_YEAR, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }

            TimePeriod.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                setStartOfDay(calendar)
                start = format.format(calendar.time)

                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }

            TimePeriod.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                setStartOfDay(calendar)
                start = format.format(calendar.time)

                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }
        }

        return Pair(start, end)
    }

    private fun setStartOfDay(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    fun ISO8601StringToSimpleDateString(iso8601String: String): String {
        return try {
            val date = format.parse(iso8601String)
            val customFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            customFormat.format(date)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}
