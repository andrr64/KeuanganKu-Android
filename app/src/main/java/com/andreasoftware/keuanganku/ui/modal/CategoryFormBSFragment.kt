package com.andreasoftware.keuanganku.ui.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.SealedDataOperationResult
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFormBSFragment(val parentView: View) : BottomSheetDialogFragment() {
    private val viewModel: CategoryFormBSViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_bottomsheet_form_category, container, false)
        val buttonSubmit = view.findViewById<Button>(R.id.btn_submit)
        val editTextCategoryName = view.findViewById<EditText>(R.id.et_categoryName)

        buttonSubmit.setOnClickListener {
            viewModel.insert(editTextCategoryName.text.toString()) { result ->
                if (result is SealedDataOperationResult.Success) {
                    AppSnackBar.success(parentView, "Category added successfully")
                } else {
                    HandleExceptionWithSnackbar.show(parentView, (result as SealedDataOperationResult.Error).errorMessage!!)
                }
                dismiss()
            }
        }
        return view
    }

    companion object {
        fun show(fragmentManager: FragmentManager, parentView: View) {
            val bottomSheetFragment = CategoryFormBSFragment(parentView)
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
        }
    }
}