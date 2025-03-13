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

    @Query("SELECT COUNT(*) FROM wallet")
    fun getCount(): LiveData<Long?>

    @Insert
    suspend fun insert(wallet: WalletModel): Long

    @Query("SELECT balance FROM wallet WHERE id = :walletId")
    suspend fun getBalance(walletId: Long): Double?

    @Query("SELECT SUM(balance) FROM wallet")
    fun getTotalBalance(): LiveData<Double?>

    @Query("UPDATE wallet SET balance = balance - :balance WHERE id = :walletId")
    suspend fun subtractBalance(walletId: Long, balance: Double)

    @Query("UPDATE wallet SET balance = balance + :balance WHERE id = :walletId")
    suspend fun addBalance(walletId: Long, balance: Double)
}