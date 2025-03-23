package com.andreasoftware.keuanganku.ui.activity.main.page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PageCategoryFormViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val transactionType = MutableLiveData(TransactionType.EXPENSE)
    val insertResult = MutableLiveData<SealedDataOperationResult<Any>>()

    fun setTransactionType(type: TransactionType) {
        transactionType.value = type
    }

    fun insert(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newCategory = CategoryModel(
                name = name,
                transactionTypeId = transactionType.value!!.value
            )
            val result = categoryRepository.insert(newCategory)
            withContext(Dispatchers.Main) {
                insertResult.value = result
            }
        }
    }
}