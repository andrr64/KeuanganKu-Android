package com.andreasoftware.keuanganku.ui.common

import android.view.View
import androidx.core.content.ContextCompat
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.ui.GlobalUIState
import com.google.android.material.snackbar.Snackbar

object AppSnackBar {
    fun success(view: View, message: String) {
        showSnackbar(view, message, ContextCompat.getColor(view.context, R.color.green), android.graphics.Color.WHITE)
    }

    fun warning(view: View, message: String) {
        showSnackbar(view, message, android.graphics.Color.YELLOW, android.graphics.Color.BLACK)
    }

    fun error(view: View, message: String) {
        showSnackbar(view, message, ContextCompat.getColor(view.context, R.color.red), android.graphics.Color.WHITE)
    }

    private fun showSnackbar(view: View, message: String, backgroundColor: Int, textColor: Int) {
        if (GlobalUIState.isSnackbarAvailable) {
            GlobalUIState.makeSnackbarUnavailable() //Prevent multiple snackbars

            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(backgroundColor)
                .setTextColor(textColor)
                .addCallback(object : Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        GlobalUIState.makeSnackbarAvailable() //Re-enable snackbars after dismissal
                    }
                })
                .show()

        }

    }
}