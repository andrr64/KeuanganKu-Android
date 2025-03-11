package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.model.IncomeCategoryModel
import com.andreasoftware.keuanganku.data.model.IncomeModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.PageIncomeFormBinding
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithModal
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageIncomeForm : Fragment() {

    private var _binding: PageIncomeFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PageIncomeFormVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PageIncomeFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        setupObservers()
        Log.d("IncomeFormPage", "Created!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("IncomeFormPage", "Destroyed!")
        _binding = null
    }

    private fun initializeUI() {
        setupDropdownHints()
        setupAppBar()
        setupClickListeners()
    }

    private fun setupAppBar() {
        binding.appBar.appBarBackButton.setOnClickListener { navigateUp() }
    }

    private fun setupDropdownHints() {
        binding.dropdownSpinnerCategory.dropdownTextInputLayout.hint = getString(R.string.select_category)
        ///TODO: use string resource
        binding.dropdownSpinnerWallet.dropdownTextInputLayout.hint = "Select Wallet"
    }

    private fun setupClickListeners() {
        binding.submitButton.setOnClickListener { eventOnSubmitButtonClicked() }
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner, ::observeCategories)
        viewModel.wallets.observe(viewLifecycleOwner, ::observeWallets)
    }

    private fun observeCategories(categories: List<IncomeCategoryModel>?) {
        categories?.let { setupCategoryDropdown(it) }
    }

    private fun observeWallets(wallets: List<WalletModel>?) {
        wallets?.let { setupWalletDropdown(it) }
    }

    private fun setupWalletDropdown(wallets: List<WalletModel>) {
        val adapter = createArrayAdapter(wallets.map { it.name })
        val autoCompleteTextView = binding.dropdownSpinnerWallet.dropdownAutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setText(wallets[0].name, false)
        viewModel.setSelectedWallet(wallets[0])
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSelectedWallet(wallets[position])
        }
    }

    private fun setupCategoryDropdown(categories: List<IncomeCategoryModel>) {
        val adapter = createArrayAdapter(categories.map { it.name })
        val autoCompleteTextView = binding.dropdownSpinnerCategory.dropdownAutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setText(categories[0].name, false)
        viewModel.setSelectedCategory(categories[0])
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSelectedCategory(categories[position])
        }
    }

    private fun createArrayAdapter(items: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, items)
    }

    private fun eventOnSubmitButtonClicked() {
        if (!validateInput()) return
        val income = createIncomeModel() ?: return
        viewModel.insertIncome(income) { result ->
            if (result.isError()) {
                ///TODO: use string resource
                HandleExceptionWithModal.info(requireContext(), "Error", result.errorMessage.toString())
            } else {
                ///TODO: use string resource
                AppSnackBar.success(binding.root, "Income added successfully")
                findNavController().navigateUp()
            }
        }
    }

    private fun validateInput(): Boolean {
        val title = binding.titleEditText.text.toString()
        val amountString = binding.amountEditText.text.toString()
        var isValid = true

        binding.titleInputLayout.error = null
        binding.amountInputLayout.error = null

        if (title.isEmpty()) {
            ///TODO: use string resource
            binding.titleInputLayout.error = "Title cannot be empty"
            isValid = false
        }

        if (amountString.isEmpty()) {
            ///TODO: use string resource
            binding.amountInputLayout.error = "Amount cannot be empty"
            isValid = false
        }

        if (!isValid) return false

        try {
            amountString.toDouble()
        } catch (e: NumberFormatException) {
            HandleExceptionWithSnackbar.show(binding.root, e.toString())
            return false
        }

        if (viewModel.selectedCategory.value == null) {
            ///TODO: use string resource
            HandleExceptionWithSnackbar.show(binding.root, "Category cannot be empty")
            return false
        }
        return true
    }

    private fun createIncomeModel(): IncomeModel? {
        val title = binding.titleEditText.text.toString()
        val amountString = binding.amountEditText.text.toString()
        val selectedCategory = viewModel.selectedCategory.value ?: return null
        val amount = amountString.toDouble()

        return IncomeModel(
            description = title,
            amount = amount,
            category_id = selectedCategory.id,
            rating = 5,
            date = System.currentTimeMillis(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            wallet_id = viewModel.selectedWallet.value?.id ?: 0
        )
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}