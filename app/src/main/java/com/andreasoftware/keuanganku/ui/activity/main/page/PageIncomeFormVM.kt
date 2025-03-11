package com.andreasoftware.keuanganku.ui.activity.main.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.model.IncomeCategoryModel
import com.andreasoftware.keuanganku.data.model.IncomeModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.IncomeCategoryRepository
import com.andreasoftware.keuanganku.data.repository.IncomeRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageIncomeFormVM
@Inject constructor(
    incomeCategoryRepository: IncomeCategoryRepository,
    walletRepository: WalletRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    val categories: LiveData<List<IncomeCategoryModel>> = incomeCategoryRepository.allCategories
    val wallets: LiveData<List<WalletModel>> = walletRepository.allWallet

    private val _selectedCategory = MutableLiveData<IncomeCategoryModel?>()
    val selectedCategory: LiveData<IncomeCategoryModel?> get() = _selectedCategory

    private val _selectedWallet = MutableLiveData<WalletModel?>()
    val selectedWallet: LiveData<WalletModel?> get() = _selectedWallet

    fun setSelectedCategory(category: IncomeCategoryModel) {
        _selectedCategory.value = category
    }

    fun setSelectedWallet(wallet: WalletModel) {
        _selectedWallet.value = wallet
    }

    fun insertIncome(income: IncomeModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            val result = incomeRepository.insertIncome(income)
            callback(result)
        }
    }
}