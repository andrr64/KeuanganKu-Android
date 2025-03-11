package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.exception.WalletDAOException
import com.andreasoftware.keuanganku.data.model.IncomeModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject constructor(
    private val walletDao: WalletDao,
    private val incomeDao: IncomeDao,
    private val db: AppDatabase
) {

    companion object {
        private const val WALLET_INIT_CATEGORY_ID = 1L
        private const val WALLET_INIT_RATING = 5
        private const val WALLET_INIT_DESCRIPTION_PREFIX = "Wallet '"
        private const val WALLET_INIT_DESCRIPTION_SUFFIX = "' init"
    }

    val allWallet: LiveData<List<WalletModel>> = walletDao.getAll()
    val totalAmount: LiveData<Double> = walletDao.getTotalBalance().map { it ?: 0.0 }

    suspend fun insertWallet(wallet: WalletModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                db.withTransaction {
                    val insertedId = walletDao.insert(wallet) // Tangkap ID yang dikembalikan
                    val newIncome = IncomeModel(
                        description = "$WALLET_INIT_DESCRIPTION_PREFIX${wallet.name}$WALLET_INIT_DESCRIPTION_SUFFIX",
                        amount = wallet.balance,
                        wallet_id = insertedId,
                        category_id = WALLET_INIT_CATEGORY_ID,
                        rating = WALLET_INIT_RATING,
                        date = System.currentTimeMillis(),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    incomeDao.insert(newIncome)
                }
                return@withContext DataOperationResult(true)
            } catch (e: Exception) {
                return@withContext DataOperationResult(false, WalletDAOException.CREATE_ERROR.code, e.toString())
            }
        }
    }

    val count: LiveData<Long?> = walletDao.getCount()
}