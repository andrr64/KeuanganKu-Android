package com.andreasoftware.keuanganku.ui.activity.main.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import com.andreasoftware.keuanganku.data.repository.cache.IncomeCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletFormPageViewModel
@Inject constructor(val repository: WalletRepository, val incomeCache: IncomeCacheRepository) : ViewModel()
{
    fun insertWallet(wallet: WalletModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            val result = repository.insertWallet(wallet)
            callback(result)
        }
    }
}