package com.andreasoftware.keuanganku.ui.activity.main.page

import android.util.Log
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
class PageExpenseFormViewModel
@Inject constructor(
    categoryRepository: CategoryRepository,
    walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository,
) : ViewModel() {

    val categories: LiveData<List<CategoryModel>> = categoryRepository.expenseCategories
    val wallets: LiveData<List<WalletModel>> = walletRepository.allWallet
    private val _rating: MutableLiveData<Int> = MutableLiveData(1)
    val rating: LiveData<Int> get() = _rating

    fun setRating(rating: Int) {
        _rating.value = rating
    }

    fun getRating(): Int? {
        return _rating.value
    }

    private val _selectedCategory = MutableLiveData<CategoryModel?>()
    val selectedCategory: LiveData<CategoryModel?> get() = _selectedCategory

    private val _selectedWallet = MutableLiveData<WalletModel?>()
    val selectedWallet: LiveData<WalletModel?> get() = _selectedWallet

    fun setSelectedCategory(category: CategoryModel) {
        Log.d("PageExpenseFormViewModel", "setSelectedCategory: $category")
        _selectedCategory.value = category
    }

    fun setSelectedWallet(wallet: WalletModel) {
        Log.d("PageExpenseFormViewModel", "setSelectedWallet: $wallet")
        _selectedWallet.value = wallet
    }

    fun insertExpense(expense: TransactionModel, callback: (DataOperationResult) -> Unit) {
        viewModelScope.launch {
            Log.d("PageExpenseFormViewModel", "Inserting expense: $expense")
            val result = transactionRepository.insertExpense(expense)
            Log.d("PageExpenseFormViewModel", "Insert result: $result")
            callback(result)
        }
    }
}
