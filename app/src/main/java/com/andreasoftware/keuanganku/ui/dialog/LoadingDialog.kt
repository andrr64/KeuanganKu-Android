package com.andreasoftware.keuanganku.ui.dialog

import android.app.Dialog
import android.content.Context
import com.andreasoftware.keuanganku.R

class LoadingDialog(private val context: Context) {
    private var loadingDialog: Dialog? = null

    fun show() {
        loadingDialog = Dialog(context)
        loadingDialog?.setContentView(R.layout.dialog_loading)
        loadingDialog?.setCancelable(false)
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog?.show()
    }

    fun hide() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}