package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IncomeFormViewModel : ViewModel() {

    private val _spinnerSelectedText = MutableLiveData<String?>(null)
    val spinnerSelectedText: LiveData<String?> = _spinnerSelectedText

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