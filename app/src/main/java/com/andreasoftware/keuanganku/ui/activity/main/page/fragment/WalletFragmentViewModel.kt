package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.data.repository.ExpenseLimiterRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletFragmentViewModel
@Inject constructor(
    walletRepository: WalletRepository,
    private val expenseLimiterRepository: ExpenseLimiterRepository,
    val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _wallets = walletRepository.allWallet
    val wallets: LiveData<List<WalletModel>> = _wallets

    val expenseLimiters: LiveData<List<ExpenseLimiterModel>> = expenseLimiterRepository.allExpenseLimitersLiveData
}