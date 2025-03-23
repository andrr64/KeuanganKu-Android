package com.andreasoftware.keuanganku.ui.activity.main.page

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.common.TransactionType
import com.andreasoftware.keuanganku.databinding.PageCategoryFormBinding
import com.andreasoftware.keuanganku.ui.KSubPage
import com.andreasoftware.keuanganku.ui.common.Adapter
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageCategoryForm : KSubPage<PageCategoryFormBinding, PageCategoryFormViewModel>() {
    override val viewModel: PageCategoryFormViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PageCategoryFormBinding = PageCategoryFormBinding.inflate(inflater, container, false)

    override fun setupComponent() {
        super.setupComponent()
        binding.etName.setText("")
        binding.incDropdownTransactionType.dropdownTextInputLayout.hint = "What type of transaction is this category?"
        Adapter.setupDropdownAdapter<TransactionType>(
            requireContext(),
            items = TransactionType.entries,
            displayText = TransactionType.entries.map { it -> TransactionType.getDisplayName(it.value) },
            autoCompleteTextView = binding.incDropdownTransactionType.dropdownAutoCompleteTextView,
            onItemSelected = {value -> viewModel.setTransactionType(value)}
        )
    }

    override fun setupListener() {
        super.setupListener()
        binding.submitButton.setOnClickListener {
            viewModel.insert(binding.etName.text.toString())
        }
    }

    override fun setupObserver() {
        super.setupObserver()
        viewModel.insertResult.observe(viewLifecycleOwner) {
            when (it) {
                is SealedDataOperationResult.Success -> {
                    ///TODO: handle ketika success
                    AppSnackBar.success(binding.root, "Data berhasil disimpan")
                    findNavController().navigateUp()
                }
                is SealedDataOperationResult.Error -> {
                    HandleExceptionWithSnackbar.show(binding.root, it.errorMessage?: "")
                }
            }
        }
    }
}