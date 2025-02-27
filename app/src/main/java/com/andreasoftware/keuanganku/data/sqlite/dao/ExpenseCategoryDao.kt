package com.andreasoftware.keuanganku.data.sqlite.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.sqlite.entities.ExpenseCategory

@Dao
interface ExpenseCategoryDao {
    @Insert
    suspend fun insert(category: ExpenseCategory)

    @Query("SELECT COUNT(*) FROM expense_categories")
    suspend fun getCount(): Int

    @Query("SELECT * FROM expense_categories")
    fun getAll(): LiveData<List<ExpenseCategory>>
}