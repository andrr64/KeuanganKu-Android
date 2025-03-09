package com.andreasoftware.keuanganku.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.IncomeModel

@Dao
interface IncomeDao {
    @Query("""
        SELECT * FROM income 
        WHERE (:startTime IS NULL OR date >= :startTime) 
        AND (:endTime IS NULL OR date <= :endTime)
        LIMIT :limit OFFSET :offset
    """)
    fun getAllStrict(
        startTime: Long?,
        endTime: Long?,
        limit: Int,
        offset: Int
    ): List<IncomeModel>

    @Insert
    suspend fun insert(income: IncomeModel)
}