package com.andreasoftware.keuanganku.common.enums

enum class PeriodOptions(val label: String) {
    WEEKLY("This Week"),
    MONTHLY("This Month"),
    YEARLY("This Year");

    companion object {
        fun fromString(value: String): PeriodOptions? {
            return entries.find { it.label.equals(value, ignoreCase = true) }
        }
    }
}
