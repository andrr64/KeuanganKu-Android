package com.andreasoftware.keuanganku.data.dao.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.expenseCacheDatastore by preferencesDataStore(name = "expense_cache")

class ExpenseCacheDS
@Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val AMOUNT_THIS_WEEK_KEY = longPreferencesKey("amount_this_week")
        private val AMOUNT_THIS_MONTH_KEY = longPreferencesKey("amount_this_month")
        private val AMOUNT_THIS_YEAR_KEY = longPreferencesKey("amount_this_year")
    }

    val amountThisWeek: Flow<Long?> = context.expenseCacheDatastore.data.map { preferences ->
        preferences[AMOUNT_THIS_WEEK_KEY]
    }

    val amountThisMonth: Flow<Long?> = context.expenseCacheDatastore.data.map { preferences ->
        preferences[AMOUNT_THIS_MONTH_KEY]
    }

    val amountThisYear: Flow<Long?> = context.expenseCacheDatastore.data.map { preferences ->
        preferences[AMOUNT_THIS_YEAR_KEY]
    }

    suspend fun saveAmountThisWeek(amount: Long) {
        context.expenseCacheDatastore.edit { preferences ->
            preferences[AMOUNT_THIS_WEEK_KEY] = amount
        }
    }

    suspend fun saveAmountThisMonth(amount: Long) {
        context.expenseCacheDatastore.edit { preferences ->
            preferences[AMOUNT_THIS_MONTH_KEY] = amount
        }
    }

    suspend fun saveAmountThisYear(amount: Long) {
        context.expenseCacheDatastore.edit { preferences ->
            preferences[AMOUNT_THIS_YEAR_KEY] = amount
        }
    }
}