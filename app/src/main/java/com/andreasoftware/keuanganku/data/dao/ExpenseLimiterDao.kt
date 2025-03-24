package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.common.ISO8601String
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel

@Dao
interface ExpenseLimiterDao {
    @Query("SELECT * FROM expense_limiters")
    fun getAllExpenseLimitersLiveData(): LiveData<List<ExpenseLimiterModel>>

    @Query("SELECT * FROM expense_limiters")
    fun getAllExpenseLimiters(): List<ExpenseLimiterModel>

    @Insert
    fun insert(expenseLimiter: ExpenseLimiterModel)

    @Query("UPDATE expense_limiters SET usedAmount = usedAmount+:amount WHERE walletId = :walletId AND categoryId = :categoryId AND enumTimePeriodValue >= :enumTimePeriodValue")
    fun addUsedAmount(walletId: Long, categoryId: Long, enumTimePeriodValue: Short, amount: Double)

    @Query("DELETE FROM expense_limiters WHERE id = :id")
    fun delete(id: Long)

    @Query("SELECT SUM(amount) FROM transactions WHERE categoryId = :categoryId AND date >= :startDate AND date <= :endDate AND transactionTypeId = :expense")
    suspend fun calculateUsedAmount(
        categoryId: Long,
        startDate: ISO8601String,
        endDate: ISO8601String,
        expense: Int = TransactionType.EXPENSE.value
    ): Double?
}