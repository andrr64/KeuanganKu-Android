package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.WalletModel

@Dao
interface WalletDao {
    @Query("SELECT * FROM wallet")
    fun getAll(): LiveData<List<WalletModel>>
}