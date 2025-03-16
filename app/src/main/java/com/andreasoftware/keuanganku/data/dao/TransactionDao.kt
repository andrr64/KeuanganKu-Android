package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.common.enm.TransactionType
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
        startDate: Long,
        endDate: Long
    ): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE transactionTypeId = :income AND date >= :startDate AND date <= :endDate")
    suspend fun totalIncome(
        income: Int = TransactionType.INCOME.value,
        startDate: Long,
        endDate: Long
    ): Double?

    @Query("SELECT * FROM transactions WHERE date >= :start AND date <= :end ORDER BY date DESC LIMIT :limit")
    suspend fun getRecentTransactions(start: Long, end: Long, limit: Int): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :start AND date <= :end ORDER BY date ASC LIMIT :limit")
    suspend fun getTransactionsDateAscending(
        start: Long,
        end: Long,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :start AND date <= :end ORDER BY date DESC LIMIT :limit")
    suspend fun getTransactionsDateDescending(
        start: Long,
        end: Long,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :start AND date <= :end ORDER BY amount ASC LIMIT :limit")
    suspend fun getTransactionsAmountAscending(
        start: Long,
        end: Long,
        limit: Int
    ): List<TransactionModel>

    @Query("SELECT * FROM transactions WHERE date >= :start AND date <= :end ORDER BY amount DESC LIMIT :limit")
    suspend fun getTransactionsAmountDescending(
        start: Long,
        end: Long,
        limit: Int
    ): List<TransactionModel>
}
