package com.andreasoftware.keuanganku.ui.exceptionhandler

import android.view.View
import androidx.core.content.ContextCompat
import com.andreasoftware.keuanganku.R
import com.google.android.material.snackbar.Snackbar

class HandleExceptionWithSnackbar {
    companion object {
        fun failed(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(view.context, R.color.red ))
                .setTextColor(android.graphics.Color.WHITE)
                .show()
        }
    }
}