package com.andreasoftware.keuanganku.data.dao.app

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.userDatastore by preferencesDataStore(name = "userdata")

class UserdataDS @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val NAME_KEY = stringPreferencesKey("name")
        private val CURRENCY_LOCALE_KEY = stringPreferencesKey("currency_locale")
    }

    fun getName(): Flow<String> = context.userDatastore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[NAME_KEY] ?: "none" } // Default "Guest"

    fun getCurrencyLocale(): Flow<String> = context.userDatastore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[CURRENCY_LOCALE_KEY] ?: "IDR" } // Default "IDR"

    suspend fun saveName(name: String) {
        context.userDatastore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }

    suspend fun saveCurrencyLocale(locale: String) {
        context.userDatastore.edit { preferences ->
            preferences[CURRENCY_LOCALE_KEY] = locale
        }
    }

    suspend fun isUsernameExists(): Boolean {
        val currentName = context.userDatastore.data.map { preferences ->
            preferences[NAME_KEY]
        }.first() // Ambil nilai pertama secara langsung (suspend function)

        return !currentName.isNullOrEmpty()
    }
}
