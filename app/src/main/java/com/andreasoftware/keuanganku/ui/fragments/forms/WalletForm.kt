package com.andreasoftware.keuanganku.ui.fragments.forms

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.database.entities.Wallet
import com.andreasoftware.keuanganku.ui.dialog.LoadingDialog
import com.andreasoftware.keuanganku.ui.viewmodels.WalletFormViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletForm : Fragment() {
    private lateinit var submitButton: Button
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var titleEditText: TextInputEditText
    private lateinit var amountEditText: TextInputEditText
    private lateinit var titleInputLayout: TextInputLayout
    private lateinit var amountInputLayout: TextInputLayout
    private val walletViewModel: WalletFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_app_form_wallet, container, false)
        val appBar = view.findViewById<View>(R.id.appBar)

        appBar.findViewById<View>(R.id.appBarBackButton).setOnClickListener {
            findNavController().navigateUp()
        }
        appBar.findViewById<TextView>(R.id.appBarTitle).text = getString(R.string.wallet_form)
        submitButton = view.findViewById(R.id.submitButton)
        loadingDialog = LoadingDialog(requireContext())
        titleEditText = view.findViewById(R.id.titleEditText)
        amountEditText = view.findViewById(R.id.amountEditText)
        titleInputLayout = view.findViewById(R.id.titleInputLayout)
        amountInputLayout = view.findViewById(R.id.amountInputLayout)

        submitButton.setOnClickListener {
            onSubmit()
        }
        return view
    }
    ///TODO: binding

    private fun onSubmit() {
        val title = titleEditText.text.toString().trim()
        val amount = amountEditText.text.toString().trim()

        if (title.isEmpty()) {
            titleInputLayout.error = "Title cannot be empty"
            return
        } else {
            titleInputLayout.error = null
        }

        if (amount.isEmpty()) {
            amountInputLayout.error = "Amount cannot be empty"
            return
        } else {
            amountInputLayout.error = null
        }

        loadingDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            loadingDialog.hide()
            val wallet = Wallet(title = title, balance = amount.toDouble())
            walletViewModel.insert(wallet)
            Snackbar.make(requireView(), "Success!", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green))
                .show()
            findNavController().navigateUp()
        }, 500)
    }
}