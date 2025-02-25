package com.andreasoftware.keuanganku.data.database.dao

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.database.entities.Income

@Dao
interface IncomeDao {
    @Insert
    suspend fun insert(income: Income)

    @Query("SELECT * FROM incomes")
    fun getAllIncomes(): LiveData<List<Income>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :startDate AND :endDate")
    fun getIncomeByPeriods(startDate: String, endDate: String): LiveData<List<Income>>

    @Query("SELECT SUM(amount) FROM incomes WHERE date BETWEEN:startDate AND:endDate")
    fun getTotalIncomeByPeriods(startDate: Long, endDate: Long): LiveData<Double?>

    fun getTotalIncomeByPeriodOption(periodOptions: PeriodOptions): LiveData<Double?> {
        val calendar = Calendar.getInstance()
        val endDate = calendar.timeInMillis
        val startDate = when (periodOptions) {
            PeriodOptions.WEEKLY -> {
                calendar.add(Calendar.DAY_OF_YEAR, -7)
                calendar.timeInMillis
            }
            PeriodOptions.MONTHLY -> {
                calendar.add(Calendar.MONTH, -1)
                calendar.timeInMillis
            }
            PeriodOptions.YEARLY -> {
                calendar.add(Calendar.YEAR, -1)
                calendar.timeInMillis
            }
        }
        val results = getTotalIncomeByPeriods(startDate, endDate)
        return results
    }
}