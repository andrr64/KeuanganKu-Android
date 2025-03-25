package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.dao.TransactionDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.db.AppDatabase
import com.andreasoftware.keuanganku.data.exception.WalletDAOException
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject constructor(
    private val walletDao: WalletDao,
    private val transactionDao: TransactionDao,
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

    suspend fun insertWallet(newWallet: WalletModel): SealedDataOperationResult<Long> {
        return try {
            val insertedId = db.withTransaction {
                walletDao.insert(newWallet)
            }
            val newIncome = TransactionModel(
                title = "Create new wallet '${newWallet.name}'",
                description = "$WALLET_INIT_DESCRIPTION_PREFIX${newWallet.name}$WALLET_INIT_DESCRIPTION_SUFFIX",
                amount = newWallet.balance,
                walletId = insertedId,
                categoryId = WALLET_INIT_CATEGORY_ID,
                rating = WALLET_INIT_RATING,
                transactionTypeId = TransactionType.INCOME.value,
            )
            db.withTransaction {
                transactionDao.insert(newIncome)
            }

            SealedDataOperationResult.Success(insertedId)
        } catch (e: Exception) {
            SealedDataOperationResult.Error(
                errorCode = WalletDAOException.CREATE_ERROR.code,
                errorMessage = e.localizedMessage ?: "Unknown error"
            )
        }
    }

    val count: LiveData<Long?> = walletDao.getCount()
}