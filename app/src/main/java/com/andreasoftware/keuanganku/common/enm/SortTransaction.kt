package com.andreasoftware.keuanganku.common.enm

enum class SortTransaction(val displayName: String) {
    DATE_A_Z("Oldest"),
    DATE_Z_A("Newest"),
    AMOUNT_A_Z("Lowest"),
    AMOUNT_Z_A("Highest")
}