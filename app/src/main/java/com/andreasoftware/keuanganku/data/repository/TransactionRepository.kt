package com.andreasoftware.keuanganku.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.common.cls.DataOperationResult2
import com.andreasoftware.keuanganku.common.enm.SortTransaction
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.dao.TransactionDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.ExpenseDAOException
import com.andreasoftware.keuanganku.data.exception.IncomeDAOException
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.util.TimeUtility
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
    val countTransaction = transactionDao.countExpenseLiveData()

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

    suspend fun getRecentTransactions(timePeriod: TimePeriod, limit: Int, sortBy: SortTransaction = SortTransaction.DATE_Z_A):List<TransactionModel> {
        val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)
        Log.d("TransactionRepository", "Start: $start, End: $end")

        return when (sortBy) {
            SortTransaction.DATE_A_Z -> transactionDao.getTransactionsDateAscending(start, end, limit)
            SortTransaction.DATE_Z_A -> transactionDao.getTransactionsDateDescending(start, end, limit)
            SortTransaction.AMOUNT_A_Z -> transactionDao.getTransactionsAmountAscending(start, end, limit)
            SortTransaction.AMOUNT_Z_A -> transactionDao.getTransactionsAmountDescending(start, end, limit)
        }
    }

    suspend fun totalExpense(
        timePeriod: TimePeriod,
        startDate: Date? = null,
        endDate: Date? = null
    ): DataOperationResult2<Any> {
        val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)
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
        val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)
        try {
            val result = transactionDao.totalIncome(startDate = start, endDate = end)
            return DataOperationResult2.success(result ?: 0.0)
        } catch (e: Exception) {
            return DataOperationResult2.error(errorCode = IncomeDAOException.READ_ERROR.code, errorMessage = e.localizedMessage
                ?: "Unknown error")
        }
    }
}
