package com.andreasoftware.keuanganku.common

enum class TimePeriod(val value: TimePeriodEnumValue, val displayName: String) {
    WEEK(1, "This Week"),
    MONTH(2, "This Month"),
    YEAR(3, "This Year");

    companion object {
        fun getByDisplayName(displayName: String): TimePeriod? {
            return TimePeriod.entries.find { it.displayName == displayName }
        }

        fun getDisplayNameByValue(value: Short): String{
            return TimePeriod.entries.find { it.value == value }?.displayName ?: "Unknown Time Period"
        }
    }
}