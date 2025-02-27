package com.andreasoftware.keuanganku.data.sqlite.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.qualifier.ExpenseCategoryDaoQualifier
import com.andreasoftware.keuanganku.data.sqlite.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.entities.ExpenseCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseCategoryRepository @Inject constructor(
    @ExpenseCategoryDaoQualifier dao: ExpenseCategoryDao
) {
    val allCategories: LiveData<List<ExpenseCategory>> = dao.getAll()
}