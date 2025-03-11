package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.IncomeDAOException
import com.andreasoftware.keuanganku.data.model.IncomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeRepository
@Inject constructor(
    private val incomeDao: IncomeDao,
    private val walletDao: WalletDao,
    private val db: AppDatabase) : ViewModel()
{
    suspend fun insertIncome(income: IncomeModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                db.withTransaction {
                    incomeDao.insert(income)
                    walletDao.addBalance(income.wallet_id, income.amount)
                }
                return@withContext DataOperationResult(true)
            } catch (e: Exception){
                return@withContext DataOperationResult(false, IncomeDAOException.CREATE_ERROR.code, e.toString())
            }
        }
    }
}