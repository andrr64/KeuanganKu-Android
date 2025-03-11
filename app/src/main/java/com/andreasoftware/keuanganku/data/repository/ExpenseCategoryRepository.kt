package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.model.ExpenseCategoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseCategoryRepository
@Inject constructor(expenseCategoryDao: ExpenseCategoryDao){
    val allCategories: LiveData<List<ExpenseCategoryModel>> = expenseCategoryDao.getAll()
}