package com.andreasoftware.keuanganku.ui.activity.main.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.data.repository.TransactionRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageIncomeFormViewModel
@Inject constructor(
    categoryRepository: CategoryRepository,
    walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val categories: LiveData<List<CategoryModel>> = categoryRepository.incomeCategories
    val wallets: LiveData<List<WalletModel>> = walletRepository.allWallet

    private val _selectedCategory = MutableLiveData<CategoryModel?>()
    val selectedCategory: LiveData<CategoryModel?> get() = _selectedCategory

    private val _selectedWallet = MutableLiveData<WalletModel?>()
    val selectedWallet: LiveData<WalletModel?> get() = _selectedWallet

    fun setSelectedCategory(category: CategoryModel) {
        _selectedCategory.value = category
    }

    fun setSelectedWallet(wallet: WalletModel) {
        _selectedWallet.value = wallet
    }

    fun insertIncome(income: TransactionModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            val result = transactionRepository.insertIncome(income)
            callback(result)
        }
    }
}