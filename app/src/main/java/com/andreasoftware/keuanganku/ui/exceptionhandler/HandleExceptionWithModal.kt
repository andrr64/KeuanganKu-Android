package com.andreasoftware.keuanganku.ui.exceptionhandler

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.andreasoftware.keuanganku.ui.GlobalUIState

object HandleExceptionWithModal {
    fun info(context: Context, title: String, message: String) {
        if (GlobalUIState.isModalAvailable) {
            GlobalUIState.makeModalUnavailable()

            val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    GlobalUIState.makeModalAvailable() // Mengaktifkan modal lagi setelah ditutup
                }
                .create()
            dialog.show()
        }
    }
}