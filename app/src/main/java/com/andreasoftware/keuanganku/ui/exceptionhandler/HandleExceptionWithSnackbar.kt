package com.andreasoftware.keuanganku.ui.exceptionhandler

import android.view.View
import com.andreasoftware.keuanganku.ui.common.AppSnackBar

object HandleExceptionWithSnackbar {
    fun show(view: View, message: String) {
        AppSnackBar.error(view, message)
    }
}