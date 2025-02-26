package com.andreasoftware.keuanganku.ui.fragments.forms

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentAppFormIncomeBinding
import com.andreasoftware.keuanganku.ui.viewmodels.IncomeFormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IncomeForm : Fragment() {
    private var _binding: FragmentAppFormIncomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: IncomeFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppFormIncomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.setSpinnerSelectedText(null)
        _binding= null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppbar()
        setupTextInputListeners()
        setupSpinner()
    }

    private fun setupAppbar() {
        binding.pageAppbar.apply {
            appBarTitle.text = getString(R.string.income_form)
            appBarBackButton.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setupTextInputListeners() {
        binding.titleEditText.setText(viewModel.title.value)
        binding.amountEditText.setText(viewModel.amount.value)

        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setTitle(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setAmount(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSpinner() {
        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.title }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                categoryNames
            )

            binding.spinnerIncomeCategories.spinnerAutoComplete.apply {
                setAdapter(adapter)
                if (categories.isNotEmpty() && viewModel.spinnerSelectedText.value == null) {
                    setText(categories[0].title, false)
                } else {
                    setText(viewModel.spinnerSelectedText.value, false)
                }
                onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    viewModel.setSpinnerSelectedText(selectedItem)
                }
            }
        }
    }
}