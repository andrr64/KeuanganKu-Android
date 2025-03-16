package com.andreasoftware.keuanganku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
)
data class TransactionModel (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val amount: Double,
    val walletId: Long,
    val categoryId: Long,
    val transactionTypeId: Int,
    val rating: Int,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long
)