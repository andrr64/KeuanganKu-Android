package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentWalletFormPageBinding

class WalletFormPage : Fragment() {

    private var _binding: FragmentWalletFormPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletFormPageBinding.inflate(inflater, container, false)
        return binding.root
    }
}