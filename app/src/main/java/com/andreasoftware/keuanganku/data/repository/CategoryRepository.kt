package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.model.CategoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository
@Inject constructor(
    private val categoryDao: CategoryDao
){
    val expenseCategories: LiveData<List<CategoryModel>> = categoryDao.getExpenseCategories()
    val incomeCategories: LiveData<List<CategoryModel>> = categoryDao.getIncomeCategories()
}