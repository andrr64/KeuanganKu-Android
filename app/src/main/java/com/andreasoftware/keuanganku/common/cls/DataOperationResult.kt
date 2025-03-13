package com.andreasoftware.keuanganku.common.cls

class DataOperationResult(
    internal val success: Boolean,
    private val errorCode: Long? = null,
    internal val errorMessage: String? = null
) {
    fun isError(): Boolean = !success
    fun isSuccess(): Boolean = success

    @OptIn(ExperimentalStdlibApi::class)
    fun errMsg(): String? = "Error Code ($errorCode): $errorMessage"
    fun errCode(): Long? = errorCode

    companion object {
        fun success(): DataOperationResult = DataOperationResult(true)
        fun error(errorCode: Long, errorMessage: String): DataOperationResult =
            DataOperationResult(false, errorCode, errorMessage)
    }
}