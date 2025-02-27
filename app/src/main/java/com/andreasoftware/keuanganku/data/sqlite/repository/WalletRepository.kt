package com.andreasoftware.keuanganku.data.sqlite.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.qualifier.WalletDaoQualifier
import com.andreasoftware.keuanganku.data.sqlite.dao.WalletDao
import com.andreasoftware.keuanganku.data.sqlite.entities.Wallet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository @Inject constructor(
    @WalletDaoQualifier private val walletDao: WalletDao,
) {
    val allWallets: LiveData<List<Wallet>> = walletDao.getAllWallets()
    val totalBalance: LiveData<Double> = walletDao.totalBalance()
    val totalBalanceFlow: Flow<Double> = walletDao.totalBalanceFlow().flowOn(Dispatchers.IO)

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