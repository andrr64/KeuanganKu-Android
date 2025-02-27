package com.andreasoftware.keuanganku.data.sqlite.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val balance: Double,
    val date: Long = System.currentTimeMillis()
)
