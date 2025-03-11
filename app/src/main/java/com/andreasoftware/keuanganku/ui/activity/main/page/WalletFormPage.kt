package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.FragmentWalletFormPageBinding
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFormPage : Fragment() {
    private var _binding: FragmentWalletFormPageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WalletFormPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletFormPageBinding.inflate(inflater, container, false)
        Log.d("WalletFormPage.kt", "onCreateView called")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WalletFormPage.kt", "onDestroy called")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.appBarBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.submitButton.setOnClickListener {
            eventOnButtonSubmitClicked()
        }
    }

    fun eventOnButtonSubmitClicked(){
        val newWallet = WalletModel(
            name = binding.titleEditText.text.toString(),
            balance = binding.amountEditText.text.toString().toDouble()
        )
        viewModel.insertWallet(newWallet) {
            if (it.isSuccess()) {
                AppSnackBar.success(binding.root, "Success!")
                findNavController().navigateUp()
            } else {
                HandleExceptionWithSnackbar.show(binding.root, it.errMsg()!!)
            }
        }
    }
}