package com.andreasoftware.keuanganku.data.repository

import com.andreasoftware.keuanganku.data.dao.ExpenseCategoryDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseCategoryRepository
@Inject constructor(private val  expenseCategoryDao: ExpenseCategoryDao){

}