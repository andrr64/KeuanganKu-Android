package com.andreasoftware.keuanganku.ui.activity.intro.page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andreasoftware.keuanganku.databinding.PageIntroPage2Binding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.ui.exceptionhandler.HandleExceptionWithSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PageIntro2 : Fragment() {

    private var _binding: PageIntroPage2Binding? = null
    private val binding get()= _binding!!
    private val viewModel: PageIntro2VM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PageIntroPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener {
            val name = binding.usernameField.text.toString()
            if (name.isEmpty()) {
                binding.usernameField.error = "Please enter your name"
            } else {
                lifecycleScope.launch {
                    viewModel.saveNewName(name) { success, errorMessage ->
                        if (success){
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            HandleExceptionWithSnackbar.show(binding.root, errorMessage ?: "An error occurred")
                        }
                    }
                }
            }
        }
    }
}