package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.ExpenseModel

@Dao
interface ExpenseDao {
    @Query(
        """
        SELECT * FROM expense 
        WHERE (:startTime IS NULL OR date >= :startTime) 
        AND (:endTime IS NULL OR date <= :endTime)
        LIMIT :limit OFFSET :offset
    """
    )
    fun getAllStrict(
        startTime: Long?,
        endTime: Long?,
        limit: Int,
        offset: Int
    ): List<ExpenseModel>

    @Insert
    suspend fun insert(expense: ExpenseModel): Long

    @Query(
        """
        SELECT SUM(amount) FROM expense 
        WHERE date >= :startDate AND date <= :endDate
    """
    )
    fun once_sumExpense(startDate: Long, endDate: Long): Double?

    @Query("SELECT COUNT(*) FROM expense")
    fun countExpense(): LiveData<Long?>
}