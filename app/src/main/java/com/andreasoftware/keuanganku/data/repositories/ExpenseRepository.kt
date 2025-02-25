package com.andreasoftware.keuanganku.data.repositories

import android.app.Application
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.database.dao.ExpenseDao

class ExpenseRepository(application: Application) {
    private val expenseDao: ExpenseDao = AppDatabase.getDatabase(application).expenseDao()
}