package com.andreasoftware.keuanganku.data.database.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.andreasoftware.keuanganku.data.database.entities.Income
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import kotlinx.coroutines.flow.Flow


@Dao
interface WalletDao {

    @Insert
    fun insert(wallet: Wallet): Long

    @Query("SELECT SUM(balance) FROM wallets")
    fun totalBalanceFlow(): Flow<Double>

    @Insert
    fun insertIncome(income: Income): Long

    @Query("SELECT * FROM wallets")
    fun getAllWallets(): LiveData<List<Wallet>>

    @Delete
    fun delete(wallet: Wallet)

    @Query("SELECT COALESCE(SUM(balance), 0.0) FROM wallets")
    fun totalBalance(): LiveData<Double>

    @Transaction
    suspend fun insertNewWallet(wallet: Wallet): Long {
        val walletId = insert(wallet)
        Log.d("WalletDao", "WalletDao | Inserted wallet with result id = $walletId")
        val incomeWithWalletId = Income(
            title = "Initial Balance",
            amount = wallet.balance,
            wallet_id = walletId.toInt(),
            category_id = 1,
            rating = 0,
            date = System.currentTimeMillis()
        )
        insertIncome(incomeWithWalletId)
        return walletId
    }
}