package com.andreasoftware.keuanganku.ui.activity.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.sqlite.entities.IncomeCategory
import com.andreasoftware.keuanganku.data.sqlite.repository.IncomeCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IncomeFormViewModel @Inject constructor(
    repository: IncomeCategoryRepository
) : ViewModel() {

    private val _spinnerSelectedText = MutableLiveData<String?>(null)
    val spinnerSelectedText: LiveData<String?> = _spinnerSelectedText

    val allCategories: LiveData<List<IncomeCategory>> = repository.allCategories

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