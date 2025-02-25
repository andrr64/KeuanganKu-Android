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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.database.AppDatabase
import com.andreasoftware.keuanganku.data.repositories.IncomeCategoryRepository
import com.andreasoftware.keuanganku.databinding.FragmentAppFormIncomeBinding
import com.andreasoftware.keuanganku.ui.viewmodels.IncomeCategoryFactory
import com.andreasoftware.keuanganku.ui.viewmodels.IncomeCategoryViewModel
import com.andreasoftware.keuanganku.ui.viewmodels.IncomeFormViewModel

class IncomeForm : Fragment() {

    private lateinit var binding: FragmentAppFormIncomeBinding
    private val incomeCategoryViewModel: IncomeCategoryViewModel by lazy {
        val dao = AppDatabase.getDatabase(requireContext()).incomeCategoryDao()
        val repository = IncomeCategoryRepository(dao)
        val factory = IncomeCategoryFactory(repository)
        ViewModelProvider(this, factory)[IncomeCategoryViewModel::class.java]
    }
    private val formViewModel: IncomeFormViewModel by lazy {
        ViewModelProvider(this)[IncomeFormViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppFormIncomeBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.titleEditText.setText(formViewModel.title.value)
        binding.amountEditText.setText(formViewModel.amount.value)

        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                formViewModel.setTitle(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.amountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                formViewModel.setAmount(s?.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSpinner() {
        incomeCategoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            val categoryNames = categories.map { it.title }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                categoryNames
            )

            binding.spinnerIncomeCategories.spinnerAutoComplete.apply {
                setAdapter(adapter)
                if (categories.isNotEmpty() && formViewModel.spinnerSelectedText.value == null) {
                    setText(categories[0].title, false)
                } else {
                    setText(formViewModel.spinnerSelectedText.value, false)
                }
                onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    formViewModel.setSpinnerSelectedText(selectedItem)
                }
            }
        }
    }
}