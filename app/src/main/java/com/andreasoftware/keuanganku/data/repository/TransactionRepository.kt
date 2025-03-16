package com.andreasoftware.keuanganku.data.repository

import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.common.cls.DataOperationResult2
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.common.util.getLongTimeByPeriod
import com.andreasoftware.keuanganku.data.dao.TransactionDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.ExpenseDAOException
import com.andreasoftware.keuanganku.data.exception.IncomeDAOException
import com.andreasoftware.keuanganku.data.model.TransactionModel
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository
@Inject constructor(
    private val walletDao: WalletDao,
    private val db: AppDatabase,
    private val transactionDao: TransactionDao
) {
    val countExpense = transactionDao.countExpenseLiveData()
    val countIncome = transactionDao.countIncomeLiveData()

    suspend fun insertIncome(transaction: TransactionModel): DataOperationResult {
        return try {
            db.withTransaction {
                transactionDao.insert(transaction)
            }
            DataOperationResult.success()
        } catch (e: Exception) {
            DataOperationResult.error(IncomeDAOException.CREATE_ERROR.code, e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun insertExpense(transaction: TransactionModel): DataOperationResult {
        return try {
            db.withTransaction {
                val currentBalance = walletDao.getBalance(transaction.walletId) ?: return@withTransaction DataOperationResult.error(
                    IncomeDAOException.CREATE_ERROR.code,
                    "Failed to retrieve wallet balance"
                )

                if (currentBalance < transaction.amount) {
                    return@withTransaction DataOperationResult.error(
                        IncomeDAOException.CREATE_ERROR.code,
                        "Insufficient balance"
                    )
                }

                transactionDao.insert(transaction)
                walletDao.subtractBalance(transaction.walletId, transaction.amount)
            }
            DataOperationResult.success()
        } catch (e: Exception) {
            DataOperationResult.error(IncomeDAOException.CREATE_ERROR.code, e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun once_getRecentTransactions(timePeriod: TimePeriod, limit: Int): DataOperationResult2<Any> {
        try {
            val (start, end) = getLongTimeByPeriod(timePeriod)
            val result = transactionDao.getRecentTransactions(start, end, limit)
            return DataOperationResult2.success(result)
        } catch (e: Exception){
            return DataOperationResult2.error(errorCode = ExpenseDAOException.READ_ERROR.code, errorMessage = e.localizedMessage ?: "Unknown error")
        }
    }

    fun getRecentTransactions(timePeriod: TimePeriod, limit: Int) = transactionDao.getRecentTransactionsLiveData(getLongTimeByPeriod(timePeriod).first, getLongTimeByPeriod(timePeriod).second, limit)


    suspend fun totalExpense(
        timePeriod: TimePeriod,
        startDate: Date? = null,
        endDate: Date? = null
    ): DataOperationResult2<Any> {
        val (start, end) = getLongTimeByPeriod(timePeriod, startDate, endDate)
        try {
            val result = transactionDao.totalExpense(startDate = start, endDate = end)
            return DataOperationResult2.success(result?: 0.0)
        } catch (e: Exception){
            return DataOperationResult2.error(errorCode = ExpenseDAOException.READ_ERROR.code, errorMessage = e.localizedMessage ?: "Unknown error")
        }
    }

    suspend fun totalIncome(
        timePeriod: TimePeriod,
        startDate: Date? = null,
        endDate: Date? = null): DataOperationResult2<Any> {
        val (start, end) = getLongTimeByPeriod(timePeriod, startDate, endDate)
        try {
            val result = transactionDao.totalIncome(startDate = start, endDate = end)
            return DataOperationResult2.success(result ?: 0.0)
        } catch (e: Exception) {
            return DataOperationResult2.error(errorCode = IncomeDAOException.READ_ERROR.code, errorMessage = e.localizedMessage
                ?: "Unknown error")
        }
    }
}
