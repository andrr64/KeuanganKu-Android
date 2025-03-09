package com.andreasoftware.keuanganku.common

class DataOperationResult (internal val success: Boolean, private val errorCode: Int? = null, internal val errorMessage: String? = null) {
    fun isError(): Boolean = !success
    fun isSuccess(): Boolean = success
    fun errMsg(): String? = errorMessage
    fun errCode(): Int? = errorCode
}