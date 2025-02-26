package com.andreasoftware.keuanganku.ui.fragments.forms

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentAppFormExpenseBinding
import com.andreasoftware.keuanganku.ui.viewmodels.ExpenseFormViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpenseForm : Fragment() {

    private var _binding: FragmentAppFormExpenseBinding? = null
    private val binding get()= _binding!!

    private val viewModel: ExpenseFormViewModel by viewModels() // mengikuti lifecycle currentCiew
    private val titleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.setTitle(s?.toString())
        }
        override fun afterTextChanged(s: Editable?) {}
    }
    private val amountTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            viewModel.setAmount(s?.toString())
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppFormExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSubmitButton()
        setupAppbar()
        setupTextListeners()
        setupSpinner()
    }

    private fun setupAppbar() {
        binding.pageAppbar.apply {
            appBarTitle.text = getString(R.string.expense_form)
            appBarBackButton.setOnClickListener { findNavController().navigateUp() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }

    private fun setupTextListeners() {
        binding.titleEditText.setText(viewModel.title.value)
        binding.amountEditText.setText(viewModel.amount.value)

        binding.titleEditText.addTextChangedListener(titleTextWatcher)
        binding.amountEditText.addTextChangedListener(amountTextWatcher)
    }

    private fun setupSpinner() {
        observeSpinnerData(
            viewModel.allCategories,
            binding.spinnerExpenseCategories.spinnerAutoComplete,
            viewModel.spinnerSelectedText
        ) { selectedItem ->
            viewModel.setSpinnerSelectedText(selectedItem)
        }

        observeSpinnerData(
            viewModel.allWallet,
            binding.spinnerWallets.spinnerAutoComplete,
            viewModel.spinnerWalletSelectedText
        ) { selectedItem ->
            viewModel.setSelectedWallet(selectedItem)
        }
    }

    private fun <T> observeSpinnerData(
        data: LiveData<List<T>>,
        spinner: AutoCompleteTextView,
        selectedText: LiveData<String?>,
        onItemSelected: (String) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            data.observe(viewLifecycleOwner) { items ->
                val itemNames = items.map { (it as? Any)?.toString() ?: "" }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    itemNames
                )
                spinner.apply {
                    setAdapter(adapter)
                    if (items.isNotEmpty() && selectedText.value == null) {
                        setText(items[0].toString(), false)
                    } else {
                        setText(selectedText.value, false)
                    }
                    onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                        val selectedItem = parent.getItemAtPosition(position).toString()
                        onItemSelected(selectedItem)
                    }
                }
            }
        }
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener { onSubmit() }
    }

    private fun onSubmit() {
        val title = binding.titleEditText.text.toString().trim()
        val amount = binding.amountEditText.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (amount.isEmpty()) {
            Toast.makeText(requireContext(), "Amount cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val message = "Expense Added:\nTitle: $title\nAmount: $amount"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}