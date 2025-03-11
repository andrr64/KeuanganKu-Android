package com.andreasoftware.keuanganku.data.repository

import androidx.lifecycle.LiveData
import com.andreasoftware.keuanganku.data.dao.IncomeCategoryDao
import com.andreasoftware.keuanganku.data.model.IncomeCategoryModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IncomeCategoryRepository
@Inject constructor(incomeCategoryDao: IncomeCategoryDao) {
    val allCategories: LiveData<List<IncomeCategoryModel>> = incomeCategoryDao.getAll()
}