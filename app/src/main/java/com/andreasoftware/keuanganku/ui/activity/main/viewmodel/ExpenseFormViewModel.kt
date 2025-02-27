package com.andreasoftware.keuanganku.ui.activity.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.sqlite.entities.ExpenseCategory
import com.andreasoftware.keuanganku.data.sqlite.entities.Wallet
import com.andreasoftware.keuanganku.data.sqlite.repository.ExpenseCategoryRepository
import com.andreasoftware.keuanganku.data.sqlite.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpenseFormViewModel @Inject constructor(
    expenseCategoryRepository: ExpenseCategoryRepository,
    walletRepository: WalletRepository
) : ViewModel() {
    private val _spinnerSelectedText = MutableLiveData<String?>(null)
    val spinnerSelectedText: LiveData<String?> = _spinnerSelectedText

    private val _spinnerWalletSelectedText = MutableLiveData<String?>(null)
    val spinnerWalletSelectedText: LiveData<String?> = _spinnerSelectedText
    fun setSelectedWallet(text: String?) {
        _spinnerWalletSelectedText.value = text
    }

    fun reset(){
        _spinnerSelectedText.value = null
        _spinnerWalletSelectedText.value = null
        _title.value = null
        _amount.value = null
    }

    val allCategories: LiveData<List<ExpenseCategory>> = expenseCategoryRepository.allCategories
    val allWallet: LiveData<List<Wallet>> = walletRepository.allWallets

    private val _title = MutableLiveData<String?>(null)
    val title: LiveData<String?> = _title

    private val _amount = MutableLiveData<String?>(null)
    val amount: LiveData<String?> = _amount

    fun setSpinnerSelectedText(text: String?) {
        _spinnerSelectedText.value = text
    }

    fun setTitle(text: String?) {
        _title.value = text
    }

    fun setAmount(text: String?) {
        _amount.value = text
    }
}