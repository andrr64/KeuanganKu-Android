package com.andreasoftware.keuanganku.ui.activity.main.page

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.data.model.CategoryModel
import com.andreasoftware.keuanganku.data.model.TransactionModel
import com.andreasoftware.keuanganku.data.model.WalletModel
import com.andreasoftware.keuanganku.databinding.PageExpenseFormBinding
import com.andreasoftware.keuanganku.ui.KSubPage
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithModal
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import com.andreasoftware.keuanganku.ui.modal.CategoryFormBSFragment
import com.andreasoftware.keuanganku.util.RatingDescription
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageExpenseForm : KSubPage<PageExpenseFormBinding, PageExpenseFormViewModel>() {

    override val viewModel: PageExpenseFormViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): PageExpenseFormBinding = PageExpenseFormBinding.inflate(inflater, container, false)

    override fun setupComponent() {
        super.setupComponent()
        setupDropdownHints()
    }

    override fun setupListener() {
        super.setupListener()
        binding.submitButton.setOnClickListener { eventOnSubmitButtonClicked() }
        binding.buttonAddCategory.setOnClickListener {
            CategoryFormBSFragment.show(parentFragmentManager)
        }
        binding.transactionRatingbarLayout.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (fromUser) {
                viewModel.setRating(rating.toInt())
            }
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewModel.categories.observe(viewLifecycleOwner, ::observeCategories)
        viewModel.wallets.observe(viewLifecycleOwner, ::observeWallets)
        viewModel.rating.observe(viewLifecycleOwner) { ratingValue ->
            binding.transactionFormRatingBarDescription.text =
                RatingDescription.getDescription(requireContext(), ratingValue)
        }
        viewModel.insertResult.observe(viewLifecycleOwner) {
            when (it) {
                is SealedDataOperationResult.Success -> {
                    AppSnackBar.success(binding.root, "Expense limiter added successfully")
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

    private fun setupDropdownHints() {
        binding.incDropdownSpinnerCategory.dropdownTextInputLayout.hint =
            getString(R.string.select_category)
        binding.incDropdownSpinnerWallet.dropdownTextInputLayout.hint = "Select Wallet"
    }

    private fun observeCategories(categories: List<CategoryModel>?) {
        categories?.let { setupCategoryDropdown(it) }
    }

    private fun observeWallets(wallets: List<WalletModel>?) {
        wallets?.let { setupWalletDropdown(it) }
    }

    private fun setupWalletDropdown(wallets: List<WalletModel>) {
        val adapter = createArrayAdapter(wallets.map { it.name })
        val autoCompleteTextView = binding.incDropdownSpinnerWallet.dropdownAutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setText(wallets[0].name, false)
        viewModel.setSelectedWallet(wallets[0])
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSelectedWallet(wallets[position])
        }
    }

    private fun setupCategoryDropdown(categories: List<CategoryModel>) {
        val adapter = createArrayAdapter(categories.map { it.name })
        val autoCompleteTextView = binding.incDropdownSpinnerCategory.dropdownAutoCompleteTextView
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
        val expense = createExpenseModel() ?: return
        viewModel.insertExpense(expense)
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
            HandleExceptionWithSnackbar.show(binding.root, "Cannot empty")
            return false
        }
        return true
    }

    private fun createExpenseModel(): TransactionModel? {
        val title = binding.titleEditText.text.toString()
        val description = binding.etDescription.text.toString()
        val amountString = binding.amountEditText.text.toString()
        val selectedCategory = viewModel.selectedCategory.value ?: return null
        val amount = amountString.toDouble()

        return TransactionModel(
            title = title,
            description = description,
            amount = amount,
            categoryId = selectedCategory.id,
            rating = viewModel.rating.value!!,
            walletId = viewModel.selectedWallet.value?.id ?: 0,
            transactionTypeId = TransactionType.EXPENSE.value
        )
    }
}