package com.andreasoftware.keuanganku.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import com.andreasoftware.keuanganku.data.repositories.ExpenseRepository
import com.andreasoftware.keuanganku.data.repositories.IncomeRepository
import com.andreasoftware.keuanganku.data.repositories.WalletRepository

class HomeViewModel (
    application: Application,
) : AndroidViewModel(application) {
    val walletRepository: WalletRepository = WalletRepository(application)
    val incomeRepository: IncomeRepository = IncomeRepository(application)
    val expenseRepository: ExpenseRepository = ExpenseRepository(application)

    val totalBalance: LiveData<Double> = walletRepository.totalBalance
    val allWallets: LiveData<List<Wallet>> = walletRepository.allWallets

    val _expensePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    val _expensePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val expensePeriod: LiveData<String> = _expensePeriod

    val _incomePeriod = MutableLiveData(PeriodOptions.WEEKLY.label)
    val _incomePeriodEnum = MutableLiveData(PeriodOptions.WEEKLY)
    val incomePeriod: LiveData<String> = _incomePeriod
    val incomeTotal: LiveData<Double?> =
        incomeRepository.getTotalIncomeByPeriodOption(_incomePeriodEnum.value!!)

    fun setExpensePeriod(period: String) {
        _expensePeriod.value = period
        _expensePeriodEnum.value = PeriodOptions.fromString(period)
    }

    fun setIncomePeriod(period: String) {
        _incomePeriod.value = period
        _incomePeriodEnum.value = PeriodOptions.fromString(period)
    }
}