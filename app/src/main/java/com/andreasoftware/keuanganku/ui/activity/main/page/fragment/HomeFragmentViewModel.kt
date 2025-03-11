package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.data.repository.ExpenseRepository
import com.andreasoftware.keuanganku.data.repository.IncomeRepository
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
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    val balance: LiveData<Double> = walletRepository.totalAmount
    val walletCount: LiveData<Long?> = walletRepository.count

    private val _expensePeriod = MutableStateFlow(TimePeriod.WEEK)
    val expensePeriod: StateFlow<TimePeriod> = _expensePeriod
    private val _incomePeriod = MutableStateFlow(TimePeriod.WEEK)
    val incomePeriod: StateFlow<TimePeriod> = _incomePeriod

    private val _totalExpense = MutableLiveData(0.0)
    val totalExpense: LiveData<Double> = _totalExpense

    private val _totalIncome = MutableLiveData(0.0)
    val totalIncome: LiveData<Double> = _totalIncome

    private val _countExpense = expenseRepository.countExpense
    private val _countIncome = incomeRepository.countIncome

    fun setExpensePeriod(period: TimePeriod) {
        _expensePeriod.value = period
        getExpense(period)
    }
    fun setIncomePeriod(period: TimePeriod){
        _incomePeriod.value = period
        getIncome(period)
    }

    init {
        viewModelScope.launch {
            _countExpense.observeForever {
                getExpense(_expensePeriod.value)
            }
        }
        viewModelScope.launch {
            _countIncome.observeForever {
                getIncome(_incomePeriod.value)
            }
        }

        viewModelScope.launch {
            userdataRepository.getUsername().collectLatest { name ->
                _userName.value = name
            }
        }
        viewModelScope.launch {
            _expensePeriod.collectLatest { period ->
                getExpense(period)
                getIncome(period)
            }
        }
    }

    private fun getExpense(period: TimePeriod) {
        viewModelScope.launch {
            val result = expenseRepository.totalExpense(period)
            if (result.isSuccess()) {
                _totalExpense.value = result.data ?: 0.0
            }
        }
    }

    private fun getIncome(period: TimePeriod) {
        viewModelScope.launch {
            val result = incomeRepository.totalIncome(period)
            if (result.isSuccess()) {
                _totalIncome.value = result.data ?: 0.0
            }
        }
    }
}