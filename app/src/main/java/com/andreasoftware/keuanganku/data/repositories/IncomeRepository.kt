package com.andreasoftware.keuanganku.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.IncomeDao
import com.andreasoftware.keuanganku.data.database.entities.Income
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeRepository(application: Application) {
    private val incomeDao: IncomeDao = AppDatabase.getDatabase(application).incomeDao()
    val allIncomes = incomeDao.getAllIncomes()

    suspend fun insert(income: Income) {
        withContext(Dispatchers.IO) {
            incomeDao.insert(income)
        }
    }

    fun getTotalIncomeByPeriodOption(periodOptions: PeriodOptions): LiveData<Double?> {
        return incomeDao.getTotalIncomeByPeriodOption(periodOptions)
    }

    suspend fun delete(income: Income) {
        withContext(Dispatchers.IO) {

        }
    }
}