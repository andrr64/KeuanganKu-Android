package com.andreasoftware.keuanganku.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.database.entities.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expense")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT COUNT(*) FROM expense")
    fun getCount(): LiveData<Int>

    @Query("SELECT SUM(amount) FROM expense WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalExpenseByPeriod(startDate: Long, endDate: Long): LiveData<Double?>
}