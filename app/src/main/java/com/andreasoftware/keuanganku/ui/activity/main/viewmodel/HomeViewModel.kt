package com.andreasoftware.keuanganku.ui.activity.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.datastore.UserDataStore
import com.andreasoftware.keuanganku.data.sqlite.entities.Income
import com.andreasoftware.keuanganku.data.sqlite.entities.Wallet
import com.andreasoftware.keuanganku.data.sqlite.repository.ExpenseRepository
import com.andreasoftware.keuanganku.data.sqlite.repository.IncomeRepository
import com.andreasoftware.keuanganku.data.sqlite.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository,
    private val walletRepository: WalletRepository,
    private val userdata: UserDataStore
) : ViewModel() {

    val totalBalance: LiveData<Double> = walletRepository.totalBalance
    val allWallets: LiveData<List<Wallet>> = walletRepository.allWallets
    val allIncomes: LiveData<List<Income>> = incomeRepository.allIncome
    val username: Flow<String> = userdata.usernameFlow

    private val _totalExpenses: LiveData<Int> = expenseRepository.getExpenseCount()
    private val _expensePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    private val _expensePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val expensePeriod: LiveData<String> = _expensePeriod
    val expenseTotal = _expensePeriodEnum.switchMap { period ->
        _totalExpenses.switchMap {
            Log.d("HomeViewModel", "Expense Total Updated by COUNTS: $it")
            expenseRepository.getTotalIncomeByPeriodOption(period)
        }
    }

    private val _totalIncomes: LiveData<Int> = incomeRepository.getIncomeCount()
    private val _incomePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    private val _incomePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val incomePeriod: LiveData<String> = _incomePeriod
    val incomeTotal: LiveData<Double?> = _incomePeriodEnum.switchMap { period ->
        _totalIncomes.switchMap {
            Log.d("HomeViewModel", "Income Total Updated by COUNTS: $it")
            incomeRepository.getTotalIncomeByPeriodOption(period)
        }
    }

    fun setExpensePeriod(period: String) {
        _expensePeriod.value = period
        _expensePeriodEnum.value = PeriodOptions.fromString(period)
    }

    fun setIncomePeriod(period: String) {
        _incomePeriod.value = period
        val periodEnum = PeriodOptions.fromString(period)
        _incomePeriodEnum.value = periodEnum
    }
}