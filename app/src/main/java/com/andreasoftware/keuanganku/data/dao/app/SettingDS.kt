package com.andreasoftware.keuanganku.data.dao.app

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.settingDatastore by preferencesDataStore(name = "setting")
class SettingDS
@Inject constructor (@ApplicationContext private val context: Context) {
    companion object {
        private val LANGUAGE_LOCALE_KEY = stringPreferencesKey("language_locale")
    }

    val languageLocale: Flow<String?> = context.settingDatastore.data.map { preferences ->
        preferences[LANGUAGE_LOCALE_KEY]
    }

    suspend fun saveLanguageLocale(locale: String) {
        context.settingDatastore.edit { preferences ->
            preferences[LANGUAGE_LOCALE_KEY] = locale
        }
    }
}