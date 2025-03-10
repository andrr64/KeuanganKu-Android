package com.andreasoftware.keuanganku.data.exception

private const val START_ERROR_CODE: Long = 0xaa

enum class ExpenseDAOException(val code: Long, val message: String) {
    CREATE_ERROR(START_ERROR_CODE + 1, "Error creating income"),
    READ_ERROR(START_ERROR_CODE + 2, "Error reading income"),
    UPDATE_ERROR(START_ERROR_CODE + 3, "Error updating income"),
    DELETE_ERROR(START_ERROR_CODE + 4, "Error deleting income");

    fun toException(customMessage: String? = null): Exception {
        return Exception("[$code] ${customMessage ?: message}")
    }
}
