package com.andreasoftware.keuanganku.data.sqlite.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.common.qualifier.IncomeCategoryDaoQualifier
import com.andreasoftware.keuanganku.data.sqlite.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.sqlite.entities.IncomeCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeCategoryRepository @Inject constructor(
    @IncomeCategoryDaoQualifier dao: IncomeCategoryDao
) {
    val allCategories: LiveData<List<IncomeCategory>> = dao.getAll()
}