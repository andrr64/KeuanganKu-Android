package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.common.cls.DataOperationResult2
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.IncomeDAOException
import com.andreasoftware.keuanganku.data.model.IncomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeRepository
@Inject constructor(
    private val incomeDao: IncomeDao,
    private val walletDao: WalletDao,
    private val db: AppDatabase
) : ViewModel() {

    suspend fun insertIncome(income: IncomeModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                db.withTransaction {
                    incomeDao.insert(income)
                    walletDao.addBalance(income.wallet_id, income.amount)
                }
                return@withContext DataOperationResult(true)
            } catch (e: Exception) {
                return@withContext DataOperationResult(
                    false,
                    IncomeDAOException.CREATE_ERROR.code,
                    e.toString()
                )
            }
        }
    }

    suspend fun totalIncome(period: TimePeriod): DataOperationResult2<Double?> {
        return withContext(Dispatchers.IO) {
            try {
                val calendar = Calendar.getInstance()
                val endDate = calendar.timeInMillis
                val startDate = when (period) {
                    TimePeriod.WEEK -> {
                        calendar.add(Calendar.DAY_OF_YEAR, -7)
                        calendar.timeInMillis
                    }

                    TimePeriod.MONTH -> {
                        calendar.add(Calendar.MONTH, -1)
                        calendar.timeInMillis
                    }

                    TimePeriod.YEAR -> {
                        calendar.add(Calendar.YEAR, -1)
                        calendar.timeInMillis
                    }
                }
                val total = incomeDao.once_sumIncome(startDate, endDate)?.toDouble()
                return@withContext DataOperationResult2(true, data = total)
            } catch (e: Exception) {
                return@withContext DataOperationResult2(
                    false,
                    IncomeDAOException.READ_ERROR.code,
                    e.toString()
                )
            }
        }
    }

    val countIncome = incomeDao.countIncome()
}