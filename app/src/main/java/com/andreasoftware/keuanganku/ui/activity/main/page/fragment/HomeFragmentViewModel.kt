package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.model.TransactionModel
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
    private val _transactionPeriod = MutableStateFlow(TimePeriod.WEEK)
    private val _incomePeriod = MutableStateFlow(TimePeriod.WEEK)
    val expensePeriod: StateFlow<TimePeriod> = _expensePeriod
    val incomePeriod: StateFlow<TimePeriod> = _incomePeriod
    val transactionPeriod: StateFlow<TimePeriod> = _transactionPeriod

    private val _totalExpense = MutableLiveData(0.0)
    private val _totalIncome = MutableLiveData(0.0)
    val totalExpense: LiveData<Double> = _totalExpense
    val totalIncome: LiveData<Double> = _totalIncome

    val recentTransactionLimit = 5

    private val _countExpense = transactionRepository.countExpense
    private val _countIncome = transactionRepository.countIncome

    fun setExpensePeriod(period: TimePeriod) {
        _expensePeriod.value = period
    }

    fun setIncomePeriod(period: TimePeriod) {
        _incomePeriod.value = period
    }

    fun setTransactionPeriod(period: TimePeriod) {
        _transactionPeriod.value = period
    }


    val recentTransactionsX: LiveData<List<TransactionModel>> = transactionRepository.getRecentTransactions(_transactionPeriod.value, recentTransactionLimit)

    init {
        viewModelScope.launch {
            userdataRepository.getUsername().collectLatest { name ->
                _userName.value = name
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
            }
        }
    }

    private fun updateIncome() {
        viewModelScope.launch {
            val period = _incomePeriod.value
            val result = transactionRepository.totalIncome(period)
            if (result.isSuccess()) {
                _totalIncome.value = (result.data ?: 0.0) as Double
            }
        }
    }
}