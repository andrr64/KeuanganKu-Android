package com.andreasoftware.keuanganku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.util.TimeUtility
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "expense_limiters",
    foreignKeys = [
        ForeignKey(
            entity = WalletModel::class,
            parentColumns = ["id"],
            childColumns = ["walletId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryModel::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )
    ]
)
data class ExpenseLimiterModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val walletId: Long,
    val categoryId: Long,
    val everyXDay: Int,
    val enumTimePeriodValue: Int?,
    val createdAt: ISO8601String = TimeUtility.getCurrentISO8601(),
    val updatedAt: ISO8601String = TimeUtility.getCurrentISO8601()
): Parcelable