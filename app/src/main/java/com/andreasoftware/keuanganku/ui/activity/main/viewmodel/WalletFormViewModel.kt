package com.andreasoftware.keuanganku.ui.activity.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.data.sqlite.entities.Wallet
import com.andreasoftware.keuanganku.data.sqlite.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletFormViewModel @Inject constructor(
    private val repository: WalletRepository
) : ViewModel() {

    fun insert(wallet: Wallet) = viewModelScope.launch {
        repository.insert(wallet)
    }

    fun delete(wallet: Wallet) = viewModelScope.launch {
        repository.delete(wallet)
    }
}