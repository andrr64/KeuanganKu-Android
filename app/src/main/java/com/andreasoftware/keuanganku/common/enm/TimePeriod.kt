package com.andreasoftware.keuanganku.common.enm

enum class TimePeriod(val displayName: String) {
    WEEK("This Week"),
    MONTH("This Month"),
    YEAR("This Year");

    companion object {
        fun getByDisplayName(displayName: String): TimePeriod? {
            return TimePeriod.entries.find { it.displayName == displayName }
        }
    }
}