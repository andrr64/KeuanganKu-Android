package com.andreasoftware.keuanganku.data.dataclass

import com.andreasoftware.keuanganku.common.enums.TransactionType

data class ShortTransactionItem (
    val type: TransactionType,
    val title: String,
    val description: String,
    val amount: Double,
    val createdAt: Long,
    val updatedAt: Long
)
