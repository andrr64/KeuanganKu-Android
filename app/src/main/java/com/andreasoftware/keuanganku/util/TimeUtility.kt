package com.andreasoftware.keuanganku.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtility {
    @SuppressLint("ConstantLocale")
    private  val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    fun getCurrentISO8601(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return format.format(Date())
    }

    fun getTimePeriodISO8601(timePeriod: TimePeriod): Pair<String, String> {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        val start: String
        val end: String

        when (timePeriod) {
            TimePeriod.WEEK -> {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                start = format.format(calendar.time)

                calendar.add(Calendar.WEEK_OF_YEAR, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }

            TimePeriod.MONTH -> {
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                start = format.format(calendar.time)

                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }

            TimePeriod.YEAR -> {
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                start = format.format(calendar.time)

                calendar.add(Calendar.YEAR, 1)
                calendar.add(Calendar.MILLISECOND, -1)
                end = format.format(calendar.time)
            }
        }

        return Pair(start, end)
    }
}
