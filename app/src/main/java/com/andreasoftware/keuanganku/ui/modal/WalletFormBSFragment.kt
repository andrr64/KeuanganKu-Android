package com.andreasoftware.keuanganku.ui.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFormBSFragment(
    private val onSuccessfulOperation: () -> Unit,
    private val onFailedOperation: () -> Unit
) : BottomSheetDialogFragment() {

    private val viewModel: WalletFormBSViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_bottomsheet_form_wallet, container, false)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val etWalletName = view.findViewById<TextInputEditText>(R.id.titleEditText)
        val etInitialAmount = view.findViewById<TextInputEditText>(R.id.amountEditText)

        submitButton.setOnClickListener {
            val newWallet = WalletModel(
                name = etWalletName.text.toString(),
                balance = etInitialAmount.text.toString().toDouble()
            )
            viewModel.insertWallet(newWallet) {
                if (it is SealedDataOperationResult.Success){
                    onSuccessfulOperation()
                } else {
                    onFailedOperation()
                }
                dismiss()
            }
        }

        return view
    }
}