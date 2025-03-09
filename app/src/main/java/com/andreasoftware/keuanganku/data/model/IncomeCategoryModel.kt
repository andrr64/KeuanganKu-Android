package com.andreasoftware.keuanganku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_category")
data class IncomeCategoryModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis().toLong(),
    val updatedAt: Long = System.currentTimeMillis().toLong()
)
