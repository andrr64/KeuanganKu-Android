package com.andreasoftware.keuanganku.ui.exceptionhandler

import android.view.View
import com.google.android.material.snackbar.Snackbar

class HandleExceptionWithSnackbar {
    companion object {
        fun failed(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(android.graphics.Color.RED)
                .setTextColor(android.graphics.Color.WHITE)
                .show()
        }
    }
}