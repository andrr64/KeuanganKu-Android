package com.andreasoftware.keuanganku.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.util.TimeUtility

@Entity(tableName = "wallet")
data class WalletModel(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val balance: Double,
    val createdAt: ISO8601String = TimeUtility.getCurrentISO8601(),
    val updatedAt: ISO8601String = TimeUtility.getCurrentISO8601()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeDouble(balance)
        parcel.writeString(createdAt)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<WalletModel> {
        override fun createFromParcel(parcel: Parcel): WalletModel {
            return WalletModel(parcel)
        }

        override fun newArray(size: Int): Array<WalletModel?> {
            return arrayOfNulls(size)
        }
    }
}
