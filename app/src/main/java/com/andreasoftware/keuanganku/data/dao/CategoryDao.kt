package com.andreasoftware.keuanganku.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreasoftware.keuanganku.common.enm.TransactionType
import com.andreasoftware.keuanganku.data.model.CategoryModel

@Dao
interface CategoryDao {
    @Query("SELECT COUNT(*) FROM categories WHERE transactionTypeId = :transactionTypeId")
    suspend fun getCategoryCountByType(transactionTypeId: Int): Int

    @Insert
    suspend fun insert(category: CategoryModel)

    @Query("SELECT * FROM categories WHERE transactionTypeId = :categoryId AND id != 1")
    fun getIncomeCategories(categoryId: Int = TransactionType.INCOME.value): LiveData<List<CategoryModel>>

    @Query("SELECT * FROM categories WHERE transactionTypeId = :categoryId")
    fun getExpenseCategories(categoryId: Int = TransactionType.EXPENSE.value): LiveData<List<CategoryModel>>
}