package com.andreasoftware.keuanganku.ui.common

import android.view.View
import androidx.core.content.ContextCompat
import com.andreasoftware.keuanganku.R
import com.google.android.material.snackbar.Snackbar

class MySnackbar {
    companion object {

        fun success(view: View, message: String) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(view.context, R.color.green )) // Warna hijau untuk sukses
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