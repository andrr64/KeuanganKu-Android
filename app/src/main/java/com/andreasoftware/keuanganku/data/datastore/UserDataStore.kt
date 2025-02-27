package com.andreasoftware.keuanganku.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore (private val context: Context) {
    private val Context.dataStore by preferencesDataStore("user_data")

    val usernameFlow: Flow<String> = context.dataStore.data
        .map { userdata ->
            userdata[NAME_KEY] ?: "Not Found"
        }

    val isUsernameExist: Flow<Boolean> = context.dataStore.data.map { userdata ->
        userdata[NAME_KEY] != null
    }

    companion object {
        val NAME_KEY = stringPreferencesKey("name")
    }

    suspend fun saveUserName(name: String){
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }
}