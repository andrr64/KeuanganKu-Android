package com.andreasoftware.keuanganku.data.repository

import com.andreasoftware.keuanganku.data.dao.ExpenseDao
import com.andreasoftware.keuanganku.data.dao.IncomeDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository
@Inject constructor(expenseDao: ExpenseDao){
}