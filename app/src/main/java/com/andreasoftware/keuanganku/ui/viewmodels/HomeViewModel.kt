package com.andreasoftware.keuanganku.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import com.andreasoftware.keuanganku.data.repositories.ExpenseRepository
import com.andreasoftware.keuanganku.data.repositories.IncomeRepository
import com.andreasoftware.keuanganku.data.repositories.WalletRepository

class HomeViewModel (
    application: Application,
) : AndroidViewModel(application) {
    val walletRepository: WalletRepository by lazy {WalletRepository(application)}
    val incomeRepository: IncomeRepository by lazy {IncomeRepository(application)}
    val expenseRepository: ExpenseRepository by lazy {ExpenseRepository(application)}

    val totalBalance: LiveData<Double> = walletRepository.totalBalance
    val allWallets: LiveData<List<Wallet>> = walletRepository.allWallets

    val _totalExpenses: LiveData<Int> = expenseRepository.getExpenseCount()
    val _expensePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    val _expensePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val expensePeriod: LiveData<String> = _expensePeriod
    val expenseTotal = _expensePeriodEnum.switchMap { period ->
        _totalExpenses.switchMap {
            Log.d("HomeViewModel", "Expense Total Updated by COUNTS: $it")
            expenseRepository.getTotalIncomeByPeriodOption(period)
        }
    }

    val _totalIncomes: LiveData<Int> = incomeRepository.getIncomeCount()
    val _incomePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    val _incomePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val incomePeriod: LiveData<String> = _incomePeriod
    val incomeTotal: LiveData<Double?> = _incomePeriodEnum.switchMap { period ->
        _totalIncomes.switchMap {
            Log.d("HomeViewModel", "Income Total Updated by COUNTS: $it")
            incomeRepository.getTotalIncomeByPeriodOption(period)
        }
    }

    fun updateIncomeTotal(){
        _incomePeriodEnum.value = _incomePeriodEnum.value
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