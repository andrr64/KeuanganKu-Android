package com.andreasoftware.keuanganku.common

sealed class SealedDataOperationResult<out T> {
    data class Success<T>(val data: T?) : SealedDataOperationResult<T>()
    data class Error(val errorCode: Long?, val errorMessage: String?) : SealedDataOperationResult<Nothing>()
}