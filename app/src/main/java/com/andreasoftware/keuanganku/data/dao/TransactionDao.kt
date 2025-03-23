package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.TransactionModel

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionModel)

    @Query("SELECT COUNT(*) FROM transactions WHERE transactionTypeId = :expense")
    fun countExpenseLiveData(expense: Int = TransactionType.EXPENSE.value): LiveData<Int>

    @Query("SELECT COUNT(*) FROM transactions")
    fun countLiveData(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM transactions WHERE transactionTypeId = :income")
    fun countIncomeLiveData(income: Int = TransactionType.INCOME.value): LiveData<Int>

    @Query("SELECT SUM(amount) FROM transactions WHERE transactionTypeId = :expense AND date >= :startDate AND date <= :endDate")
    suspend fun totalExpense(
        expense: Int = TransactionType.EXPENSE.value,
        startDate: ISO8601String,
        endDate: ISO8601String
    ): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE transactionTypeId = :income AND date >= :startDate AND date <= :endDate")
    suspend fun totalIncome(
        income: Int = TransactionType.INCOME.value,
        startDate: ISO8601String,  // Ubah dari Long ke String (ISO 8601)
        endDate: ISO8601String
    ): Double?

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC LIMIT :limit")
    suspend fun getRecentTransactions(
        startDate: ISO8601String,
        endDate: ISO8601String,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC LIMIT :limit")
    suspend fun getTransactionsDateAscending(
        startDate: ISO8601String,
        endDate: ISO8601String,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC LIMIT :limit")
    suspend fun getTransactionsDateDescending(
        startDate: ISO8601String,
        endDate: ISO8601String,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY amount ASC LIMIT :limit")
    suspend fun getTransactionsAmountAscending(
        startDate: ISO8601String,
        endDate: ISO8601String,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :startDate AND date <= :endDate ORDER BY amount DESC LIMIT :limit")
    suspend fun getTransactionsAmountDescending(
        startDate: ISO8601String,
        endDate: ISO8601String,
        limit: Int
    ): List<TransactionModel>
}
