package com.andreasoftware.keuanganku.data.repositories

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.database.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.database.entities.ExpenseCategory
import com.andreasoftware.keuanganku.data.di.ExpenseCategoryDaoQualifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseCategoryRepository @Inject constructor(
    @ExpenseCategoryDaoQualifier dao: ExpenseCategoryDao
) {
    val allCategories: LiveData<List<ExpenseCategory>> = dao.getAll()
}