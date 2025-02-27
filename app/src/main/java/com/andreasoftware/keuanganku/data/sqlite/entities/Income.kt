package com.andreasoftware.keuanganku.data.sqlite.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "incomes",
    foreignKeys = [
        ForeignKey(
            entity = Wallet::class,
            parentColumns = ["id"],
            childColumns = ["wallet_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IncomeCategory::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["wallet_id"]), Index(value = ["category_id"])]
)
data class Income(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Double,
    val wallet_id: Int,
    val category_id: Int,
    val rating: Short,
    val date: Long = System.currentTimeMillis(),
)