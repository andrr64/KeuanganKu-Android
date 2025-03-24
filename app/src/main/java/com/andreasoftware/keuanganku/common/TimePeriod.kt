package com.andreasoftware.keuanganku.common

import android.os.Build
import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.TemporalAdjusters

enum class TimePeriod(
    val value: Short,
    val displayName: String,
    val xLyDisName: String
) {
    WEEK(1, "This Week", "Weekly"),
    MONTH(2, "This Month", "Monthly"),
    YEAR(3, "This Year", "Yearly");

    companion object {
        fun getByDisplayName(displayName: String): TimePeriod? {
            return TimePeriod.entries.find { it.displayName == displayName }
        }

        fun getDisplayNameByValue(value: Short): String {
            return TimePeriod.entries.find { it.value == value }?.displayName
                ?: "Unknown Time Period"
        }

        fun getEnumByValue(value: Short): TimePeriod? {
            return TimePeriod.entries.find { it.value == value }
        }

        fun getEnumByISO8601String(dateString: String): TimePeriod? {
            return try {
                Log.d("TimePeriod", "Date String: $dateString")
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                val zonedDateTime = ZonedDateTime.parse(dateString, formatter)
                val date = zonedDateTime.toLocalDate()

                val today = LocalDate.now()

                // Mendapatkan batas waktu untuk minggu, bulan, dan tahun
                val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth())
                val startOfYear = today.with(TemporalAdjusters.firstDayOfYear())

                Log.d("TimePeriod", "Today: $today")
                Log.d("TimePeriod", "Start of Week: $startOfWeek")
                Log.d("TimePeriod", "Start of Month: $startOfMonth")
                Log.d("TimePeriod", "Start of Year: $startOfYear")

                when {
                    date.isAfter(startOfWeek.minusDays(1)) -> WEEK // Jika tanggal >= startOfWeek
                    date.isAfter(startOfMonth.minusDays(1)) -> MONTH // Jika tanggal >= startOfMonth
                    date.isAfter(startOfYear.minusDays(1)) -> YEAR // Jika tanggal >= startOfYear
                    else -> null
                }
            } catch (e: DateTimeParseException) {
                println("Error parsing date: ${e.message}")
                null
            }
        }
    }
}