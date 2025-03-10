package com.andreasoftware.keuanganku.data.exception

private const val START_ERROR_CODE: Long = 0xab

enum class WalletDAOException(val code: Long, val message: String) {
    CREATE_ERROR(START_ERROR_CODE + 1, "Error creating wallet"),
    READ_ERROR(START_ERROR_CODE + 2, "Error reading wallet"),
    UPDATE_ERROR(START_ERROR_CODE + 3, "Error updating wallet"),
    DELETE_ERROR(START_ERROR_CODE + 4, "Error deleting wallet");

    fun toException(customMessage: String? = null): Exception {
        return Exception("[$code]: ${customMessage ?: message}")
    }
}
