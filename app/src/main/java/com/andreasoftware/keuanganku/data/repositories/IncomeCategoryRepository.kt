package com.andreasoftware.keuanganku.data.repositories

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.database.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.database.entities.IncomeCategory
import com.andreasoftware.keuanganku.data.di.IncomeCategoryDaoQualifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeCategoryRepository @Inject constructor(
    @IncomeCategoryDaoQualifier dao: IncomeCategoryDao
) {
    val allCategories: LiveData<List<IncomeCategory>> = dao.getAll()
}