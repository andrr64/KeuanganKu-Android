package com.andreasoftware.keuanganku.ui.modal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFormBSViewModel
@Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun insert(categoryName: String) {
        val newCategory =
            CategoryModel(name = categoryName, transactionTypeId = TransactionType.EXPENSE.value)
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.insert(newCategory)
        }
    }
}