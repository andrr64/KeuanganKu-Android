package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.PageDetailWalletBinding

class PageDetailWallet : Fragment() {

    private var _binding: PageDetailWalletBinding? = null
    private val binding get() = _binding!!
    private var wallet: WalletModel? = null // Properti untuk menyimpan TransactionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PageDetailWalletBinding.inflate(inflater, container, false)
        wallet = arguments?.getParcelable<WalletModel>("wallet")

        wallet?.let {
            Log.d("PageDetailWallet", "Wallet received: ${it.name}, Amount: ${it.balance}")
        } ?: Log.e("PageDetailWallet", "Wallet is null!")

        Log.d("PageDetailWallet", "onCreateView")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("PageDetailWallet", "onDestroy")
    }
}