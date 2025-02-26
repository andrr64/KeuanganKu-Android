package com.andreasoftware.keuanganku.data.repositories

import android.app.Application
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.IncomeDao
import com.andreasoftware.keuanganku.data.database.entities.Income
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeRepository @Inject constructor(application: Application) {
    private val incomeDao: IncomeDao = AppDatabase.getDatabase(application).incomeDao()
    private val _refreshTrigger = MutableLiveData<Unit>()

    fun getRefreshTrigger(): LiveData<Unit> = _refreshTrigger

    suspend fun insert(income: Income) {
        withContext(Dispatchers.IO) {
            incomeDao.insert(income)
            val oldUnit = _refreshTrigger.value
            _refreshTrigger.postValue(Unit)
            Log.d(
                "IncomeRepository",
                "Unit Changed: ${oldUnit.hashCode()} ->  ${_refreshTrigger.hashCode()}"
            )
        }
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
        return incomeDao.getTotalIncomeByPeriod(startDate, endDate)
    }

    fun getIncomeCount(): LiveData<Int> {
        return incomeDao.getCount()
    }

    suspend fun delete(income: Income) {
        withContext(Dispatchers.IO) {
            incomeDao.delete(income)
            _refreshTrigger.postValue(Unit)
        }
    }
}