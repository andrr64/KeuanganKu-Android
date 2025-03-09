package com.andreasoftware.keuanganku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.common.AppDatabase
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import com.andreasoftware.keuanganku.data.dao.WalletDao
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
        private const val TAG = "WalletRepository"
        private const val WALLET_INIT_CATEGORY_ID = 1L
        private const val WALLET_INIT_RATING = 5
        private const val WALLET_INIT_DESCRIPTION_PREFIX = "Wallet '"
        private const val WALLET_INIT_DESCRIPTION_SUFFIX = "' init"
        private const val ERROR_CODE_WALLET_INSERT = 101
    }

    val allWallet: LiveData<List<WalletModel>> = walletDao.getAll()
    val totalAmount: LiveData<Double> = walletDao.getTotalAmount().map { it ?: 0.0 }

    suspend fun insertWallet(wallet: WalletModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Starting transaction to insert wallet and initial income")
                db.withTransaction {
                    Log.d(TAG, "Inserting wallet: ${wallet.name}, ID before insert: ${wallet.id}")
                    val insertedId = walletDao.insert(wallet) // Tangkap ID yang dikembalikan
                    Log.d(TAG, "Inserted new wallet: ${wallet.name}, ID: $insertedId")

                    val newIncome = IncomeModel(
                        description = "$WALLET_INIT_DESCRIPTION_PREFIX${wallet.name}$WALLET_INIT_DESCRIPTION_SUFFIX",
                        amount = wallet.balance,
                        wallet_id = insertedId, // Konversi ke Int jika IncomeModel wallet_id Int.
                        category_id = WALLET_INIT_CATEGORY_ID,
                        rating = WALLET_INIT_RATING,
                        date = System.currentTimeMillis(),
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )
                    Log.d(TAG, "Inserting initial income for wallet: ${wallet.name}, wallet_id: ${newIncome.wallet_id}")
                    incomeDao.insert(newIncome)
                    Log.d(TAG, "Transaction successful for wallet: ${wallet.name}")
                }
                return@withContext DataOperationResult(true)
            } catch (exception: Exception) {
                Log.e(TAG, "Error during transaction to insert wallet and initial income", exception)
                return@withContext DataOperationResult(false, ERROR_CODE_WALLET_INSERT, "Failed to insert wallet or income: ${exception.message}")
            }
        }
    }
}