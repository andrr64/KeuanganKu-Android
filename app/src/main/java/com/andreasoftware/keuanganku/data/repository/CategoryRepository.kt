package com.andreasoftware.keuanganku.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.data.dao.CategoryDao
import com.andreasoftware.keuanganku.data.model.CategoryModel
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository
@Inject constructor(
    private val categoryDao: CategoryDao
) {
    val expenseCategories: LiveData<List<CategoryModel>> = categoryDao.getExpenseCategories()
    val incomeCategories: LiveData<List<CategoryModel>> = categoryDao.getIncomeCategories()

    private val _categoriesHashmap: HashMap<Long, CategoryModel> = HashMap()

    init {
        expenseCategories.observeForever { updateHashMap() }
        incomeCategories.observeForever { updateHashMap() }
    }

    suspend fun insert(newCategory: CategoryModel): SealedDataOperationResult<Any>{
        try {
            categoryDao.insert(newCategory)
            Log.d("CategoryRepository", "Category inserted successfully")
            return SealedDataOperationResult<Any>.Success(null)
        } catch (e: Exception){
            Log.d("CategoryRepository", "Error inserting category: ${e.message}")
            return SealedDataOperationResult.Error(null, e.message)
        }
    }

    private fun updateHashMap() {
        _categoriesHashmap.clear()
        expenseCategories.value?.let { _categoriesHashmap.putAll(it.associateBy { category -> category.id }) }
        incomeCategories.value?.let { _categoriesHashmap.putAll(it.associateBy { category -> category.id }) }
    }

    fun getCategoryById(id: Long): CategoryModel? {
        return _categoriesHashmap[id]
    }
}
