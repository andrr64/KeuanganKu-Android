package com.andreasoftware.keuanganku.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import com.andreasoftware.keuanganku.data.repositories.WalletRepository
import kotlinx.coroutines.launch

class WalletFormViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WalletRepository(application)

    fun insert(wallet: Wallet) = viewModelScope.launch {
        repository.insert(wallet)
    }

    fun delete(wallet: Wallet) = viewModelScope.launch {
        repository.delete(wallet)
    }
}