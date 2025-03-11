package com.andreasoftware.keuanganku.ui.exceptionhandler

import android.view.View
import androidx.core.content.ContextCompat
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.google.android.material.snackbar.Snackbar

object HandleExceptionWithSnackbar {
    fun show(view: View, message: String) {
        AppSnackBar.error(view, message)
    }
}