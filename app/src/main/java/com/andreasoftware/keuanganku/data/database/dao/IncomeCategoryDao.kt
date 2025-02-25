package com.andreasoftware.keuanganku.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.database.entities.IncomeCategory

@Dao
interface IncomeCategoryDao {
    @Insert
    suspend fun insert(category: IncomeCategory)

    @Query("SELECT COUNT(*) FROM income_categories")
    suspend fun getCount(): Int

    @Query("SELECT * FROM income_categories")
    fun getAll(): LiveData<List<IncomeCategory>>

    @Query("SELECT * FROM income_categories WHERE id = :id")
    fun getById(id: Int): LiveData<IncomeCategory>
}