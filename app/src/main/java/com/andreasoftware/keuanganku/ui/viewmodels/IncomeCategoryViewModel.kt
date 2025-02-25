package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.database.entities.IncomeCategory
import com.andreasoftware.keuanganku.data.repositories.IncomeCategoryRepository

class IncomeCategoryViewModel(repo: IncomeCategoryRepository) : ViewModel() {
    val allCategories: LiveData<List<IncomeCategory>> = repo.allCategories
}