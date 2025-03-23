package com.andreasoftware.keuanganku.ui.activity.main.page

import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.common.DataOperationResult2
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TimePeriod
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.ExpenseLimiterModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.PageExpenseLimiterFormBinding
import com.andreasoftware.keuanganku.ui.KSubPage
import com.andreasoftware.keuanganku.ui.common.Adapter
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PageExpenseLimiterForm : KSubPage<PageExpenseLimiterFormBinding, PageExpenseLimiterFormViewModel>() {
    override val viewModel: PageExpenseLimiterFormViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PageExpenseLimiterFormBinding = PageExpenseLimiterFormBinding.inflate(inflater, container, false)

    override fun setupComponent() {
        super.setupComponent()
        binding.incDropdownSpinnerCategory.dropdownTextInputLayout.hint = "Category"
        binding.incDropdownPeriod.dropdownTextInputLayout.hint = "Period"
        binding.incDropdownSpinnerWallet.dropdownTextInputLayout.hint = "Wallet"
        binding.etLimitAmount.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun setupListener() {
        super.setupListener()
        binding.submitButton.setOnClickListener {
            val selectedWallet = viewModel.selectedWallet
            val selectedCategory = viewModel.selectedCategory
            val selectedTimePeriod = viewModel.selectedTimePeriod
            val limitAmountText = binding.etLimitAmount.text.toString()

            if (selectedWallet == null || selectedCategory == null || selectedTimePeriod == null || limitAmountText.isEmpty()) {
                AppSnackBar.error(binding.root, "Please fill all fields")
                return@setOnClickListener
            }

            try {
                val limitAmount = limitAmountText.toDouble()
                val expenseLimiter = ExpenseLimiterModel.generateFromUI(
                    binding.etName,
                    binding.etDescription,
                    selectedWallet,
                    selectedCategory,
                    selectedTimePeriod.value,
                    limitAmount
                )
                viewModel.insert(expenseLimiter)
            } catch (e: NumberFormatException) {
                Toast.makeText(requireContext(), "Invalid limit amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewModel.insertResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is SealedDataOperationResult.Success -> {
                    AppSnackBar.success(binding.root, "Expense limiter added successfully")
                    findNavController().navigateUp()
                }
                is SealedDataOperationResult.Error -> {
                    AppSnackBar.error(binding.root, result.errorMessage ?: "An error occurred")
                }
            }
        }
    }

    override fun setupAdapter() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            Adapter.setupDropdownAdapter(
                requireContext(),
                categories,
                categories.map { it.name },
                binding.incDropdownSpinnerCategory.dropdownAutoCompleteTextView,
                onItemSelected = { viewModel.selectedCategory = it }
            )
        }

        viewModel.wallets.observe(viewLifecycleOwner) { wallets ->
            Adapter.setupDropdownAdapter(
                requireContext(),
                wallets,
                wallets.map { it.name },
                binding.incDropdownSpinnerWallet.dropdownAutoCompleteTextView,
                onItemSelected = { viewModel.selectedWallet = it }
            )
        }

        Adapter.setupDropdownAdapter(
            requireContext(),
            TimePeriod.entries,
            TimePeriod.entries.map { it.xLyDisName },
            binding.incDropdownPeriod.dropdownAutoCompleteTextView,
            onItemSelected = { viewModel.selectedTimePeriod = it }
        )
    }
}