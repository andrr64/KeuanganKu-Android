package com.andreasoftware.keuanganku.ui

class GlobalUIState {
    companion object {
        private var _snackbarAvailable = true

        fun makeSnackbarUnavailable(){
            _snackbarAvailable = false
        }

        fun makeSnackbarAvailable(){
            _snackbarAvailable = true
        }

        val isSnackbarAvailable: Boolean get() = _snackbarAvailable
    }
}