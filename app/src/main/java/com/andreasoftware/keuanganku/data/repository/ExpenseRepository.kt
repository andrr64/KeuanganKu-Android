package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.common.cls.DataOperationResult2
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.ExpenseDAOException
import com.andreasoftware.keuanganku.data.model.ExpenseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository
@Inject constructor(
    private val expenseDao: ExpenseDao,
    private val walletDao: WalletDao,
    private val db: AppDatabase): ViewModel()
{
    suspend fun insertExpense(expense: ExpenseModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                db.withTransaction {
                    expenseDao.insert(expense)
                    walletDao.subtractBalance(expense.wallet_id, expense.amount)
                }
                return@withContext DataOperationResult(true)
            } catch (e: Exception){
                return@withContext DataOperationResult(false, ExpenseDAOException.CREATE_ERROR.code, e.toString())
            }
        }
    }
    suspend fun totalExpense(period: TimePeriod): DataOperationResult2<Double?> {
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
                val total = expenseDao.once_sumExpense(startDate, endDate)?.toDouble()
                return@withContext DataOperationResult2(true, data = total)
            } catch (e: Exception) {
                return@withContext DataOperationResult2(false, ExpenseDAOException.READ_ERROR.code, e.toString())
            }
        }
    }
    val countExpense = expenseDao.countExpense()
}