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
    fun countExpense(expense: Int = TransactionType.EXPENSE.value): LiveData<Int>

    @Query("SELECT COUNT(*) FROM transactions WHERE transactionTypeId = :income")
    fun countIncome(income: Int = TransactionType.INCOME.value): LiveData<Int>

    @Query("SELECT SUM(amount) FROM transactions WHERE transactionTypeId = :expense AND date >= :startDate AND date <= :endDate")
    suspend fun totalExpense(expense: Int = TransactionType.EXPENSE.value, startDate: Long, endDate: Long): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE transactionTypeId = :income AND date >= :startDate AND date <= :endDate")
    suspend fun totalIncome(income: Int = TransactionType.INCOME.value, startDate: Long, endDate: Long): Double?
}