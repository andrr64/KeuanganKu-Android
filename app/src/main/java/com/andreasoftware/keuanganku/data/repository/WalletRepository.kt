package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.dao.WalletDao
import com.andreasoftware.keuanganku.data.model.WalletModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletRepository
@Inject constructor(walletDao: WalletDao) {
    val allWallet: LiveData<List<WalletModel>> = walletDao.getAll()
}