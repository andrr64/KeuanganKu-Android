package com.andreasoftware.keuanganku.data.repositories

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.database.dao.ExpenseCategoryDao
import com.andreasoftware.keuanganku.data.database.entities.ExpenseCategory

class ExpenseCategoryRepository(dao: ExpenseCategoryDao) {
    val allCategories: LiveData<List<ExpenseCategory>> = dao.getAll()
}