package com.andreasoftware.keuanganku.data.repository

import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.dao.ExpenseLimiterDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseLimiterRepository
@Inject constructor(
    private val expenseLimiterDao: ExpenseLimiterDao,
    private val db: AppDatabase,
    private val categoryDao: CategoryDao,
    private val walletDao: WalletDao
) {
    val allExpenseLimitersLiveData = expenseLimiterDao.getAllExpenseLimitersLiveData()

    suspend fun insert(expenseLimiter: ExpenseLimiterModel): SealedDataOperationResult<Any> {
        return try {
            db.withTransaction {
                expenseLimiterDao.insert(expenseLimiter)
            }
            SealedDataOperationResult.Success(null)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(0, e.localizedMessage ?: "Unknown error")
        }
    }
}