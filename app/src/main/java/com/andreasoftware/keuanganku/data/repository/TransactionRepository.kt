package com.andreasoftware.keuanganku.data.repository

import com.andreasoftware.keuanganku.common.TimePeriod
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.SortTransaction
import com.andreasoftware.keuanganku.data.dao.ExpenseLimiterDao
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
    private val transactionDao: TransactionDao,
    private val expenseLimiterDao: ExpenseLimiterDao
) {
    val countExpense = transactionDao.countExpenseLiveData()
    val countIncome = transactionDao.countIncomeLiveData()
    val countTransaction = transactionDao.countExpenseLiveData()

    suspend fun insertIncome(transaction: TransactionModel): SealedDataOperationResult<Unit> {
        return try {
            db.withTransaction {
                transactionDao.insert(transaction)
                walletDao.addBalance(transaction.walletId, transaction.amount)
            }
            SealedDataOperationResult.Success(null)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(IncomeDAOException.CREATE_ERROR.code, e.toString())
        }
    }

    suspend fun insertExpense(transaction: TransactionModel): SealedDataOperationResult<Unit> {
        return try {
            db.withTransaction {
                val currentBalance = walletDao.getBalance(transaction.walletId)
                    ?: return@withTransaction SealedDataOperationResult.Error(
                        IncomeDAOException.CREATE_ERROR.code,
                        "Failed to retrieve wallet balance"
                    )

                if (currentBalance < transaction.amount) {
                    return@withTransaction SealedDataOperationResult.Error(
                        IncomeDAOException.CREATE_ERROR.code,
                        "Insufficient balance"
                    )
                }
                val categoryId = transaction.categoryId
                val walletId = transaction.walletId
                val amount = transaction.amount

                val expenseLimiters = expenseLimiterDao.getLimitersByWalletAndCategory(
                    walletId = walletId,
                    categoryId = categoryId
                )
                for (limiter in expenseLimiters) {
                    expenseLimiterDao.addUsedAmount(limiter.id, amount)
                }

                transactionDao.insert(transaction)
                walletDao.subtractBalance(transaction.walletId, transaction.amount)
            }
            SealedDataOperationResult.Success(Unit)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(
                IncomeDAOException.CREATE_ERROR.code,
                e.localizedMessage ?: "Unknown error"
            )
        }
    }

    suspend fun getRecentTransactions(
        timePeriod: TimePeriod,
        limit: Int,
        sortBy: SortTransaction = SortTransaction.DATE_Z_A
    ): List<TransactionModel> {
        val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)

        return when (sortBy) {
            SortTransaction.DATE_A_Z -> transactionDao.getTransactionsDateAscending(
                start,
                end,
                limit
            )

            SortTransaction.DATE_Z_A -> transactionDao.getTransactionsDateDescending(
                start,
                end,
                limit
            )

            SortTransaction.AMOUNT_A_Z -> transactionDao.getTransactionsAmountAscending(
                start,
                end,
                limit
            )

            SortTransaction.AMOUNT_Z_A -> transactionDao.getTransactionsAmountDescending(
                start,
                end,
                limit
            )
        }
    }

    suspend fun totalExpense(
        timePeriod: TimePeriod,
        startDate: Date? = null,
        endDate: Date? = null
    ): SealedDataOperationResult<Double> {
        return try {
            val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)
            val result = transactionDao.totalExpense(startDate = start, endDate = end)
            SealedDataOperationResult.Success(result ?: 0.0)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(
                errorCode = ExpenseDAOException.READ_ERROR.code,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }
    suspend fun totalIncome(
        timePeriod: TimePeriod,
        startDate: Date? = null,
        endDate: Date? = null
    ): SealedDataOperationResult<Double> { // Change return type to SealedDataOperationResult<Double>
        return try {
            val (start, end) = TimeUtility.getTimePeriodISO8601(timePeriod)
            val result = transactionDao.totalIncome(startDate = start, endDate = end)
            SealedDataOperationResult.Success(result ?: 0.0)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(
                errorCode = IncomeDAOException.READ_ERROR.code,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }
}
