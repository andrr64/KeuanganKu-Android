package com.andreasoftware.keuanganku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (
    tableName = "categories"
)
data class CategoryModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val transactionTypeId: Int,
    val createdAt: Long = System.currentTimeMillis().toLong(),
    val updatedAt: Long = System.currentTimeMillis().toLong()
)