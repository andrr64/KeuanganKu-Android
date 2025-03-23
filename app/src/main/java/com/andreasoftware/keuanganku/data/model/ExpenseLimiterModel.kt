package com.andreasoftware.keuanganku.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.common.TimePeriodEnumValue
import com.andreasoftware.keuanganku.util.TimeUtility
import com.google.android.material.textfield.TextInputEditText
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
    ],
    indices = [
        Index("walletId"),
        Index("categoryId")
    ]
)
data class ExpenseLimiterModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val walletId: Long,
    val categoryId: Long,
    val enumTimePeriodValue: TimePeriodEnumValue?,
    val limitAmount: Double,
    val createdAt: ISO8601String = TimeUtility.getCurrentISO8601(),
    val updatedAt: ISO8601String = TimeUtility.getCurrentISO8601()
) : Parcelable {
    companion object {
        fun generateFromUI(
            titleEditText: TextInputEditText,
            descriptionEditText: TextInputEditText?,
            walletModel: WalletModel,
            categoryModel: CategoryModel,
            timePeriodValue: TimePeriodEnumValue?,
            limitAmount: Double
        ): ExpenseLimiterModel {
            return ExpenseLimiterModel(
                title = titleEditText.text.toString(),
                description = descriptionEditText?.text?.toString(),
                walletId = walletModel.id,
                categoryId = categoryModel.id,
                enumTimePeriodValue = timePeriodValue,
                limitAmount = limitAmount
            )
        }
    }
}

