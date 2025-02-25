package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.database.entities.ExpenseCategory
import com.andreasoftware.keuanganku.data.repositories.ExpenseCategoryRepository

class ExpenseCategoriesViewModel(repo: ExpenseCategoryRepository) : ViewModel() {
    val allCategories: LiveData<List<ExpenseCategory>> = repo.allCategories
}