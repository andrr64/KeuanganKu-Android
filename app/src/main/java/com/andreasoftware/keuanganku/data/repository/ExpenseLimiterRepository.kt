package com.andreasoftware.keuanganku.data.repository

import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TimePeriod
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.dao.ExpenseLimiterDao
import com.andreasoftware.keuanganku.data.dao.TransactionDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.util.TimeUtility
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseLimiterRepository
@Inject constructor(
    private val expenseLimiterDao: ExpenseLimiterDao,
    private val db: AppDatabase,
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao,
    private val walletDao: WalletDao
) {
    val allExpenseLimitersLiveData = expenseLimiterDao.getAllExpenseLimitersLiveData()

    suspend fun insert(expenseLimiter: ExpenseLimiterModel): SealedDataOperationResult<Any> {
        return try {
            db.withTransaction {
                val checkLimiter = expenseLimiterDao.getExpenseLimiterBy(
                    walletId = expenseLimiter.walletId,
                    categoryId = expenseLimiter.categoryId,
                    enumTimePeriodValue = expenseLimiter.enumTimePeriodValue!!
                )
                if (checkLimiter == null){
                    val timePeriodEnum = TimePeriod.getEnumByValue(expenseLimiter.enumTimePeriodValue)!!
                    val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriodEnum)
                    val usedAmount = expenseLimiterDao.calculateUsedAmount(
                        categoryId = expenseLimiter.categoryId,
                        startDate = start,
                        endDate = end
                    )?: 0.0
                    val resultId = expenseLimiterDao.insert(expenseLimiter)
                    if (resultId != null){
                        expenseLimiterDao.updateAmount(resultId, usedAmount)
                    } else {
                        throw Exception("Error when inserting expense limiter")
                    }
                } else {
                    throw Exception("Expense limiter already exists")
                }
            }
            SealedDataOperationResult.Success(null)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(0, e.localizedMessage ?: "Unknown error")
        }
    }
}