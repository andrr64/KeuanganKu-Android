package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.model.WalletModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject constructor(private val walletDao: WalletDao) {
    val allWallet: LiveData<List<WalletModel>> = walletDao.getAll()
    val totalAmount: LiveData<Double> = walletDao.getTotalAmount()

    suspend fun insertWallet(wallet: WalletModel): DataOperationResult {
        return withContext(Dispatchers.IO) {
            try {
                walletDao.insert(wallet)
                return@withContext DataOperationResult(true)
            } catch (exception: Exception) {
                return@withContext DataOperationResult(false, 0xff, exception.toString())
            }
        }
    }
}