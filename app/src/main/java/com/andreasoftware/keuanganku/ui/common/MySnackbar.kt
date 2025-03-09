package com.andreasoftware.keuanganku.ui.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

class MySnackbar {
    companion object {

        fun success(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(android.graphics.Color.GREEN) // Warna hijau untuk sukses
                .setTextColor(android.graphics.Color.WHITE)
                .show()
        }

        fun warning(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(android.graphics.Color.YELLOW) // Warna kuning untuk peringatan
                .setTextColor(android.graphics.Color.BLACK)
                .show()
        }
    }
}