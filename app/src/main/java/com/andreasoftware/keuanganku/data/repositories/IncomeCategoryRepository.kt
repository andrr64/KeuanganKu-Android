package com.andreasoftware.keuanganku.data.repositories

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.database.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.database.entities.IncomeCategory

class IncomeCategoryRepository(dao: IncomeCategoryDao) {
    val allCategories: LiveData<List<IncomeCategory>> = dao.getAll()
}