package com.andreasoftware.keuanganku.ui.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletFormBSViewModel
@Inject constructor(val repository: WalletRepository) : ViewModel() {
    fun insertWallet(wallet: WalletModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            val result = repository.insertWallet(wallet)
            callback(result)
        }
    }
}