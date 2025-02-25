package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andreasoftware.keuanganku.data.repositories.IncomeCategoryRepository

class IncomeCategoryFactory(private val repository: IncomeCategoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncomeCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IncomeCategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}