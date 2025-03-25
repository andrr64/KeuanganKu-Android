package com.andreasoftware.keuanganku.ui.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletFormBSViewModel
@Inject constructor(val repository: WalletRepository) : ViewModel() {
    fun insertWallet(wallet: WalletModel, callback: (SealedDataOperationResult<Any>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insertWallet(wallet)
            callback(result)
        }
    }
}