package com.andreasoftware.keuanganku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.util.TimeUtility
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val date: ISO8601String = TimeUtility.getCurrentISO8601(),
    val createdAt: ISO8601String = TimeUtility.getCurrentISO8601(),
    val updatedAt: ISO8601String = TimeUtility.getCurrentISO8601()
): Parcelable