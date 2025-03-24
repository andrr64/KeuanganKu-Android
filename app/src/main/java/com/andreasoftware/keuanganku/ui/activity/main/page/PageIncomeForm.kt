package com.andreasoftware.keuanganku.ui.activity.main.page

import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.PageIncomeFormBinding
import com.andreasoftware.keuanganku.ui.KSubPage
import com.andreasoftware.keuanganku.ui.common.Adapter
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithModal
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import com.andreasoftware.keuanganku.ui.modal.CategoryFormBSFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageIncomeForm : KSubPage<PageIncomeFormBinding, PageIncomeFormViewModel>() {

    override val viewModel: PageIncomeFormViewModel by viewModels()
    override val title: String = ""

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PageIncomeFormBinding {
        return PageIncomeFormBinding.inflate(inflater, container, false)
    }

    override fun setupComponent() {
        super.setupComponent()
        Log.d("IncomeFormPage", "Created!")
        binding.incDropdownSpinnerCategory.dropdownTextInputLayout.hint =
            getString(R.string.select_category)
        binding.incDropdownSpinnerWallet.dropdownTextInputLayout.hint = "Select Wallet"
        binding.amountEditText.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
    }

    override fun setupListener() {
        super.setupListener()
        binding.submitButton.setOnClickListener { eventOnSubmitButtonClicked() }
        binding.buttonAddCategory.setOnClickListener {
            CategoryFormBSFragment.show(parentFragmentManager, binding.root)
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewModel.categories.observe(viewLifecycleOwner, ::observeCategories)
        viewModel.wallets.observe(viewLifecycleOwner, ::observeWallets)
        viewModel.insertResult.observe(viewLifecycleOwner) {
            when (it) {
                is SealedDataOperationResult.Success -> {
                    AppSnackBar.success(binding.root, "Income added successfully")
                    findNavController().navigateUp()
                }

                is SealedDataOperationResult.Error -> {
                    HandleExceptionWithModal.info(
                        requireContext(),
                        "Error",
                        it.errorMessage.toString()
                    )
                }
            }
        }
    }

    private fun observeCategories(categories: List<CategoryModel>?) {
        categories?.let {
            Adapter.setupDropdownAdapter(
                requireContext(),
                categories,
                categories.map { it.name },
                binding.incDropdownSpinnerCategory.dropdownAutoCompleteTextView,
                onItemSelected = { viewModel.setSelectedCategory(it) }
            )
        }
    }

    private fun observeWallets(wallets: List<WalletModel>?) {
        wallets?.let {
            Adapter.setupDropdownAdapter(
                requireContext(),
                wallets,
                wallets.map { it.name },
                binding.incDropdownSpinnerWallet.dropdownAutoCompleteTextView,
                onItemSelected = { viewModel.setSelectedWallet(it) }
            )
        }
    }

    private fun eventOnSubmitButtonClicked() {
        if (!validateInput()) return
        val income = createIncomeModel() ?: return
        viewModel.insertIncome(income)
    }

    private fun validateInput(): Boolean {
        val title = binding.titleEditText.text.toString()
        val amountString = binding.amountEditText.text.toString()
        var isValid = true

        binding.titleInputLayout.error = null
        binding.amountInputLayout.error = null

        if (title.isEmpty()) {
            binding.titleInputLayout.error = "Title cannot be empty"
            isValid = false
        }

        if (amountString.isEmpty()) {
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
            HandleExceptionWithSnackbar.show(binding.root, "Category cannot be empty")
            return false
        }
        return true
    }

    private fun createIncomeModel(): TransactionModel? {
        val title = binding.titleEditText.text.toString()
        val amountString = binding.amountEditText.text.toString()
        val selectedCategory = viewModel.selectedCategory.value ?: return null
        val amount = amountString.toDouble()
        val description = binding.etDescription.text.toString()

        return TransactionModel(
            title = title,
            description = description,
            amount = amount,
            categoryId = selectedCategory.id,
            rating = 5,
            walletId = viewModel.selectedWallet.value?.id ?: 0,
            transactionTypeId = TransactionType.INCOME.value
        )
    }
}