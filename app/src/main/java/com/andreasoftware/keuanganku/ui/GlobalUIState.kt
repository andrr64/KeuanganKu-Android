package com.andreasoftware.keuanganku.ui

class GlobalUIState {
    companion object {
        private var _snackbarAvailable = true
        private var _modalAvailable = true

        fun makeSnackbarUnavailable() {
            _snackbarAvailable = false
        }

        fun makeSnackbarAvailable() {
            _snackbarAvailable = true
        }

        val isSnackbarAvailable: Boolean get() = _snackbarAvailable

        fun makeModalUnavailable() {
            _modalAvailable = false
        }

        fun makeModalAvailable() {
            _modalAvailable = true
        }

        val isModalAvailable: Boolean get() = _modalAvailable
    }
}