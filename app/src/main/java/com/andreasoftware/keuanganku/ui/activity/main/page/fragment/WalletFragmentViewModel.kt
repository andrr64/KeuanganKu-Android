package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletFragmentViewModel
@Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {
    private val _wallets = walletRepository.allWallet
    val wallets: LiveData<List<WalletModel>> = _wallets
}