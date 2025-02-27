package com.andreasoftware.keuanganku.data.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.sqlite.entities.Income

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Query("SELECT * FROM incomes")
    fun getAllIncomes(): LiveData<List<Income>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :startDate AND :endDate")
    fun getIncomeByPeriods(startDate: String, endDate: String): LiveData<List<Income>>

    @Query("SELECT COUNT(*) FROM incomes")
    fun getCount(): LiveData<Int>

    @Delete
    suspend fun delete(income: Income)

    @Query("SELECT SUM(amount) FROM incomes")
    fun getTotalIncome(): LiveData<Double?>

    @Query("SELECT SUM(amount) FROM incomes WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalIncomeByPeriod(startDate: Long, endDate: Long): LiveData<Double?>
}