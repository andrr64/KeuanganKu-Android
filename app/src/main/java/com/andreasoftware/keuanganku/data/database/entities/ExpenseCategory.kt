package com.andreasoftware.keuanganku.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_categories")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: Long = System.currentTimeMillis()
)