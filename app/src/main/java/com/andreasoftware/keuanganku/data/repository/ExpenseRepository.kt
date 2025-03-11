package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.`class`.DataOperationResult
import com.andreasoftware.keuanganku.data.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.ExpenseDAOException
import com.andreasoftware.keuanganku.data.exception.WalletDAOException
import com.andreasoftware.keuanganku.data.model.ExpenseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}