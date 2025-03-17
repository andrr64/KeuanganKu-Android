package com.andreasoftware.keuanganku.data.repository.app

import com.andreasoftware.keuanganku.common.DataOperationResult
import com.andreasoftware.keuanganku.data.dao.app.UserdataDS
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserdataRepository
@Inject constructor(private val userdataDao: UserdataDS) {

    fun getUsername(): Flow<String> = userdataDao.getName()

    fun getCurrencyLocale(): Flow<String> = userdataDao.getCurrencyLocale()

    suspend fun saveNewUsername(name: String): DataOperationResult {
        return try {
            userdataDao.saveName(name)
            DataOperationResult(true)
        } catch (e: Exception) {
            DataOperationResult(false, errorMessage = e.message, errorCode = 0xff)
        }
    }

    suspend fun saveNewCurrencyLocale(locale: String): DataOperationResult {
        return try {
            userdataDao.saveCurrencyLocale(locale)
            DataOperationResult(true)
        } catch (e: Exception) {
            DataOperationResult(false, errorMessage = e.message, errorCode = 0xff)
        }
    }

    suspend fun isUsernameExists(): Boolean {
        return userdataDao.isUsernameExists()
    }

}
