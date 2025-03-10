package com.andreasoftware.keuanganku.common

class DataOperationResult (internal val success: Boolean, private val errorCode: Long? = null, internal val errorMessage: String? = null) {
    fun isError(): Boolean = !success
    fun isSuccess(): Boolean = success
    @OptIn(ExperimentalStdlibApi::class)
    fun errMsg(): String? = "Error Code ($errorCode): $errorMessage"
    fun errCode(): Long? = errorCode
}