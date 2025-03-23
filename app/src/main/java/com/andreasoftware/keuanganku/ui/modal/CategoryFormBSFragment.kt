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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFormBSFragment : BottomSheetDialogFragment() {

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
            viewModel.insert(editTextCategoryName.text.toString())
        }
        return view
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val bottomSheetFragment = CategoryFormBSFragment()
            bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
        }
    }
}