package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.repository.TransactionRepository
import com.andreasoftware.keuanganku.data.repository.WalletRepository
import com.andreasoftware.keuanganku.data.repository.app.UserdataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel
@Inject constructor(
    userdataRepository: UserdataRepository,
    walletRepository: WalletRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    val balance: LiveData<Double> = walletRepository.totalAmount
    val walletCount: LiveData<Long?> = walletRepository.count

    private val _expensePeriod = MutableStateFlow(TimePeriod.WEEK)
    val expensePeriod: StateFlow<TimePeriod> = _expensePeriod
    private val _incomePeriod = MutableStateFlow(TimePeriod.WEEK)
    val incomePeriod: StateFlow<TimePeriod> = _incomePeriod
    private val _transactionPeriod = MutableStateFlow(TimePeriod.WEEK)
    val transactionPeriod: StateFlow<TimePeriod> = _transactionPeriod

    private val _totalExpense = MutableLiveData(0.0)
    val totalExpense: LiveData<Double> = _totalExpense

    private val _totalIncome = MutableLiveData(0.0)
    val totalIncome: LiveData<Double> = _totalIncome

    private val _countExpense = transactionRepository.countExpense
    private val _countIncome = transactionRepository.countIncome

    fun setExpensePeriod(period: TimePeriod) {
        Log.d("HomeFragmentViewModel", "setExpensePeriod: $period")
        _expensePeriod.value = period
    }

    fun setIncomePeriod(period: TimePeriod) {
        Log.d("HomeFragmentViewModel", "setIncomePeriod: $period")
        _incomePeriod.value = period
    }

    fun setTransactionPeriod(period: TimePeriod) {
        Log.d("HomeFragmentViewModel", "setTransactionPeriod: $period")
        _transactionPeriod.value = period
    }

    init {
        viewModelScope.launch {
            _countExpense.observeForever {
                Log.d("HomeFragmentViewModel", "_countExpense updated")
                getExpense(_expensePeriod.value)
            }
        }
        viewModelScope.launch {
            _countIncome.observeForever {
                Log.d("HomeFragmentViewModel", "_countIncome updated")
                getIncome(_incomePeriod.value)
            }
        }

        viewModelScope.launch {
            userdataRepository.getUsername().collectLatest { name ->
                Log.d("HomeFragmentViewModel", "Username collected: $name")
                _userName.value = name
            }
        }
        viewModelScope.launch {
            _expensePeriod.collectLatest { period ->
                Log.d("HomeFragmentViewModel", "Expense period changed: $period")
                getExpense(period)
            }
        }

        viewModelScope.launch {
            _incomePeriod.collectLatest { period ->
                Log.d("HomeFragmentViewModel", "Income period changed: $period")
                getIncome(period)
            }
        }
    }

    private fun getExpense(period: TimePeriod) {
        viewModelScope.launch {
            Log.d("HomeFragmentViewModel", "Fetching expense for period: $period")
            val result = transactionRepository.totalExpense(period)
            if (result.isSuccess()) {
                _totalExpense.value = (result.data ?: 0.0) as Double
                Log.d("HomeFragmentViewModel", "Expense fetched: ${_totalExpense.value}")
            }
        }
    }

    private fun getIncome(period: TimePeriod) {
        viewModelScope.launch {
            Log.d("HomeFragmentViewModel", "Fetching income for period: $period")
            val result = transactionRepository.totalIncome(period)
            if (result.isSuccess()) {
                _totalIncome.value = (result.data ?: 0.0) as Double
                Log.d("HomeFragmentViewModel", "Income fetched: ${_totalIncome.value}")
            }
        }
    }
}
