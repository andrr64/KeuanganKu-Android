package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreasoftware.keuanganku.databinding.FragmentIncomeFormPageBinding
import android.util.Log

class IncomeFormPage : Fragment() {

    private var _binding: FragmentIncomeFormPageBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncomeFormPageBinding.inflate(inflater, container, false)
        Log.d("IncomeFormPage", "onCreateView")
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("IncomeFormPage", "onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("IncomeFormPage", "onDestroyView")
    }
}
