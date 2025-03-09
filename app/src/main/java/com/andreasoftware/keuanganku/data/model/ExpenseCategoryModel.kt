package com.andreasoftware.keuanganku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_category")
data class ExpenseCategoryModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis().toLong()
)
