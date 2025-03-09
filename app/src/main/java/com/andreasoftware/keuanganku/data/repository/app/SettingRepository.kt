package com.andreasoftware.keuanganku.data.repository.app

import com.andreasoftware.keuanganku.data.dao.app.SettingDS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository @Inject constructor(val settingDS: SettingDS) {

    val languageLocale: Flow<String?> = settingDS.languageLocale

    suspend fun saveLanguageLocale(locale: String) {
        settingDS.saveLanguageLocale(locale)
    }

    suspend fun isLanguageLocaleEmpty(): Boolean {
        val locale = settingDS.languageLocale.firstOrNull()
        return locale.isNullOrEmpty()
    }
}
