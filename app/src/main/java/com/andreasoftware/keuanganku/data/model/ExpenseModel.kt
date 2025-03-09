package com.andreasoftware.keuanganku.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "expense",
    foreignKeys = [
        ForeignKey(entity = WalletModel::class, parentColumns = ["id"], childColumns = ["wallet_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ExpenseCategoryModel::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index("wallet_id"),
        Index("category_id")
    ])
data class ExpenseModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String?,
    val amount: Double,
    val wallet_id: Long,
    val category_id: Long,
    val rating: Int,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long
)