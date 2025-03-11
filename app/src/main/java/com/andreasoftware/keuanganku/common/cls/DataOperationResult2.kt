package com.andreasoftware.keuanganku.common.cls

class DataOperationResult2<T> (
    internal val success: Boolean,
    private val errorCode: Long? = null,
    internal val errorMessage: String? = null,
    internal val data: T? = null
) {
    fun isError(): Boolean = !success
    fun isSuccess(): Boolean = success
    @OptIn(ExperimentalStdlibApi::class)
    fun errMsg(): String? = "Error Code ($errorCode): $errorMessage"
    fun errCode(): Long? = errorCode
}