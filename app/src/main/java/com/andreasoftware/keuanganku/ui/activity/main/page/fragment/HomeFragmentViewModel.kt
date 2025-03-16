package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.enm.SortTransaction
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import com.andreasoftware.keuanganku.data.repository.TransactionRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import com.andreasoftware.keuanganku.data.repository.app.UserdataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel
@Inject constructor(
    userdataRepository: UserdataRepository,
    walletRepository: WalletRepository,
    private val categoryRepository: CategoryRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    fun getCategoryRepository(): CategoryRepository {
        return categoryRepository
    }

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    val balance: LiveData<Double> = walletRepository.totalAmount
    val walletCount: LiveData<Long?> = walletRepository.count
    private val transactionCountFlow: Flow<Int> = transactionRepository.countTransaction.asFlow()

    private val _expensePeriod = MutableStateFlow(TimePeriod.WEEK)
    private val _transactionPeriod = MutableStateFlow(TimePeriod.WEEK)
    private val _incomePeriod = MutableStateFlow(TimePeriod.WEEK)
    private val _sortTransaction = MutableStateFlow(SortTransaction.DATE_Z_A)
    val expensePeriod: StateFlow<TimePeriod> = _expensePeriod
    val incomePeriod: StateFlow<TimePeriod> = _incomePeriod
    val transactionPeriod: StateFlow<TimePeriod> = _transactionPeriod
    val sortTransaction: StateFlow<SortTransaction> = _sortTransaction

    private val _totalExpense = MutableLiveData(0.0)
    private val _totalIncome = MutableLiveData(0.0)
    val totalExpense: LiveData<Double> = _totalExpense
    val totalIncome: LiveData<Double> = _totalIncome

    val recentTransactionLimit = 5
    val recentTransactions: LiveData<List<TransactionModel>> = combine(
        _transactionPeriod,
        _sortTransaction,
        transactionCountFlow
    ) { period, sortBy, _ ->
        Log.d("HomeFragmentViewModel", "Recent transactions updated")
        transactionRepository.getRecentTransactions(period, recentTransactionLimit, sortBy)
    }.asLiveData()

    fun setSortTransaction(sortTransaction: SortTransaction) {
        _sortTransaction.value = sortTransaction
        Log.d("HomeFragmentViewModel", "Sort transaction set to: ${sortTransaction.displayName}")
    }

    private val _countExpense = transactionRepository.countExpense
    private val _countIncome = transactionRepository.countIncome

    fun setExpensePeriod(period: TimePeriod) {
        _expensePeriod.value = period
        Log.d("HomeFragmentViewModel", "Expense period set to: ${period.displayName}")
    }

    fun setIncomePeriod(period: TimePeriod) {
        _incomePeriod.value = period
        Log.d("HomeFragmentViewModel", "Income period set to: ${period.displayName}")
    }

    fun setTransactionPeriod(period: TimePeriod) {
        _transactionPeriod.value = period
        Log.d("HomeFragmentViewModel", "Transaction period set to: ${period.displayName}")
    }

    init {
        viewModelScope.launch {
            userdataRepository.getUsername().collectLatest { name ->
                _userName.value = name
                Log.d("HomeFragmentViewModel", "Username updated: $name")
            }
        }
        viewModelScope.launch {
            _expensePeriod.collectLatest {
                updateExpense()
            }
        }
        viewModelScope.launch {
            _incomePeriod.collectLatest {
                updateIncome()
            }
        }
        _countExpense.observeForever {
            updateExpense()
        }
        _countIncome.observeForever {
            updateIncome()
        }
    }

    private fun updateExpense() {
        viewModelScope.launch {
            val period = _expensePeriod.value
            val result = transactionRepository.totalExpense(period)
            if (result.isSuccess()) {
                _totalExpense.value = (result.data ?: 0.0) as Double
                Log.d("HomeFragmentViewModel", "Total expense updated: ${_totalExpense.value}")
            } else {
                Log.e("HomeFragmentViewModel", "Failed to update total expense: ${result.errorMessage}")
            }
        }
    }

    private fun updateIncome() {
        viewModelScope.launch {
            val period = _incomePeriod.value
            val result = transactionRepository.totalIncome(period)
            if (result.isSuccess()) {
                _totalIncome.value = (result.data ?: 0.0) as Double
                Log.d("HomeFragmentViewModel", "Total income updated: ${_totalIncome.value}")
            } else {
                Log.e("HomeFragmentViewModel", "Failed to update total income: ${result.errorMessage}")
            }
        }
    }
}