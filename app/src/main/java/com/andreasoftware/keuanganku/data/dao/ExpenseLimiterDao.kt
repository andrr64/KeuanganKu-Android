package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel

@Dao
interface ExpenseLimiterDao {
    @Query("SELECT * FROM expense_limiters")
    fun getAllExpenseLimitersLiveData(): LiveData<List<ExpenseLimiterModel>>

    @Query("SELECT * FROM expense_limiters")
    fun getAllExpenseLimiters(): List<ExpenseLimiterModel>

    @Insert
    fun insert(expenseLimiter: ExpenseLimiterModel)

    @Query("DELETE FROM expense_limiters WHERE id = :id")
    fun delete(id: Long)
}