package com.andreasoftware.keuanganku.data.sqlite.repository

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.common.qualifier.ExpenseDaoQualifier
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    @ExpenseDaoQualifier private val expenseDao: ExpenseDao,
) {
    fun getExpenseCount(): LiveData<Int> {
        return expenseDao.getCount()
    }

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
        return expenseDao.getTotalExpenseByPeriod(startDate, endDate)
    }
}