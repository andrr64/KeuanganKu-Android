package com.andreasoftware.keuanganku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.util.TimeUtility
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "wallet")
data class WalletModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val balance: Double,
    val createdAt: ISO8601String = TimeUtility.getCurrentISO8601(),
    val updatedAt: ISO8601String = TimeUtility.getCurrentISO8601()
) : Parcelable
