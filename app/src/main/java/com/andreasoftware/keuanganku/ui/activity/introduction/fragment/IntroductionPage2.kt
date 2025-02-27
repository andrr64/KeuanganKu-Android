package com.andreasoftware.keuanganku.ui.activity.introduction.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.databinding.FragmentIntroductionPage2Binding
import com.andreasoftware.keuanganku.ui.activity.introduction.viewmodel.IntroPage2ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroductionPage2 : Fragment() {

    private var _binding: FragmentIntroductionPage2Binding? = null
    private val binding get()= _binding!!
    private val viewModel: IntroPage2ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroductionPage2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submitButton.setOnClickListener{
            val userName = binding.userName.text.toString()
            if (userName.isEmpty()) {
                binding.userName.error = "Please enter your name"
            } else {
                lifecycleScope.launch {
                    viewModel.userDataStore.saveUserName(userName)
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}