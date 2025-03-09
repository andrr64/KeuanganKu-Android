package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.ExpenseCategoryModel

@Dao
interface ExpenseCategoryDao {
    @Query("SELECT * FROM expense_category")
    fun getAll(): LiveData<List<ExpenseCategoryModel>>

    @Query("SELECT COUNT(*) FROM expense_category")
    suspend fun getCount(): Int

    @Insert
    suspend fun insert(category: ExpenseCategoryModel)
}