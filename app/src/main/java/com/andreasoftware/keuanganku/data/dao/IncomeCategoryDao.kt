package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.IncomeCategoryModel

@Dao
interface IncomeCategoryDao {
    @Query("SELECT * FROM income_category")
    fun getAll(): LiveData<List<IncomeCategoryModel>>

    @Query("SELECT COUNT(*) FROM income_category")
    suspend fun getCount(): Int

    @Insert
    suspend fun insert(category: IncomeCategoryModel)
}