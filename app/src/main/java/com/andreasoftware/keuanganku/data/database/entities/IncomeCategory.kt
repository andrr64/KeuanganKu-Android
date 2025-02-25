package com.andreasoftware.keuanganku.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_categories")
data class IncomeCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: Long= System.currentTimeMillis()
)