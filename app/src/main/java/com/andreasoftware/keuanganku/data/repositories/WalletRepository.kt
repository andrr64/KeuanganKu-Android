package com.andreasoftware.keuanganku.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.WalletDao
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(application: Application) {
    private val walletDao: WalletDao = AppDatabase.getDatabase(application).walletDao()

    val allWallets: LiveData<List<Wallet>> = walletDao.getAllWallets()
    val totalBalance: LiveData<Double> = walletDao.totalBalance()

    suspend fun insert(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            walletDao.insertNewWallet(wallet)
        }
    }

    suspend fun delete(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            walletDao.delete(wallet)
        }
    }
}