package com.andreasoftware.keuanganku.ui.activity.main.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.`class`.DataOperationResult
import com.andreasoftware.keuanganku.data.model.ExpenseCategoryModel
import com.andreasoftware.keuanganku.data.model.ExpenseModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.ExpenseCategoryRepository
import com.andreasoftware.keuanganku.data.repository.ExpenseRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageExpenseFormVM
@Inject constructor(
    expenseCategoryRepository: ExpenseCategoryRepository,
    walletRepository: WalletRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val categories: LiveData<List<ExpenseCategoryModel>> = expenseCategoryRepository.allCategories
    val wallets: LiveData<List<WalletModel>> = walletRepository.allWallet

    private val _selectedCategory = MutableLiveData<ExpenseCategoryModel?>()
    val selectedCategory: LiveData<ExpenseCategoryModel?> get() = _selectedCategory

    private val _selectedWallet = MutableLiveData<WalletModel?>()
    val selectedWallet: LiveData<WalletModel?> get() = _selectedWallet

    fun setSelectedCategory(category: ExpenseCategoryModel) {
        _selectedCategory.value = category
    }

    fun setSelectedWallet(wallet: WalletModel) {
        _selectedWallet.value = wallet
    }

    fun insertExpense(expense: ExpenseModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            val result = expenseRepository.insertExpense(expense)
            callback(result)
        }
    }
}