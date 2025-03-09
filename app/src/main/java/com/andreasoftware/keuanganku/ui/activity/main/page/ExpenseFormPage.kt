package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreasoftware.keuanganku.databinding.FragmentExpenseFormPageBinding

class ExpenseFormPage : Fragment() {

    private var _binding: FragmentExpenseFormPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ExpenseFormPage", "onCreateView")
        _binding = FragmentExpenseFormPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ExpenseFormPage", "onCreate")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ExpenseFormPage", "onDestroyView")
        _binding = null
    }
}