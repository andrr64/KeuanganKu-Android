package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.WalletModel

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet")
    fun getAll(): LiveData<List<WalletModel>>

    @Insert
    suspend fun insert(wallet: WalletModel)

    @Query("SELECT SUM(amount) FROM wallet")
    fun getTotalAmount(): LiveData<Double?>
}