package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreasoftware.keuanganku.data.repositories.ExpenseCategoryRepository

class ExpenseCategoriesFactory(private val repository: ExpenseCategoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ExpenseCategoriesViewModel::class.java)) {
            return ExpenseCategoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}