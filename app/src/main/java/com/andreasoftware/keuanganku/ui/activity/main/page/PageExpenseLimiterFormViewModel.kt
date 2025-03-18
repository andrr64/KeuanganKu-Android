package com.andreasoftware.keuanganku.ui.activity.main.page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.DataOperationResult2
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TimePeriod
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.data.repository.ExpenseLimiterRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageExpenseLimiterFormViewModel
@Inject constructor(
    private val expenseLimiterRepository: ExpenseLimiterRepository,
    categoryRepository: CategoryRepository,
    walletRepository: WalletRepository
) : ViewModel() {
    val wallets = walletRepository.allWallet
    val categories = categoryRepository.expenseCategories

    var selectedCategory: CategoryModel? = null
    var selectedWallet: WalletModel? = null
    var selectedTimePeriod: TimePeriod? = null

    private val _insertResult = MutableLiveData<SealedDataOperationResult<Any>>()
    val insertResult: LiveData<SealedDataOperationResult<Any>> = _insertResult

    fun insert(expenseLimiter: ExpenseLimiterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = expenseLimiterRepository.insert(expenseLimiter)
            _insertResult.postValue(result)
        }
    }
}