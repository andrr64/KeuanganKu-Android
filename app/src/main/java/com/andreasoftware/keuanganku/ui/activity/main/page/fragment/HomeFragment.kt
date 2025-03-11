package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.andreasoftware.keuanganku.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.ui.common.MySnackbar
import com.google.android.material.button.MaterialButton

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddWallet.button.setOnClickListener {
            navigateTo(MainActivity.ACTION_MAIN_TO_WALLET_FORM)
        }

        viewModel.walletCount.observe(viewLifecycleOwner) { walletCount ->
            if (walletCount != null && walletCount > 0) {
                binding.buttonAddIncome.button.setOnClickListener {
                    navigateTo(MainActivity.ACTION_MAIN_TO_INCOME_FORM)
                }
                binding.buttonAddExpense.button.setOnClickListener {
                    navigateTo(MainActivity.ACTION_MAIN_TO_EXPENSE_FORM)
                }
            } else {
                binding.buttonAddIncome.button.setOnClickListener {
                    MySnackbar.error(binding.root, "Anda belum memiliki dompet!")
                }
                binding.buttonAddExpense.button.setOnClickListener {
                    MySnackbar.error(binding.root, "Anda belum memiliki dompet!")
                }
            }
        }

        observeUsername()
        observeBalance()
    }

    @SuppressLint("SetTextI18n")
    private fun observeBalance(){
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.totalBalanceTextView.text = "IDR $balance"
        }
    }

    private fun observeUsername(){
        lifecycleScope.launch {
            viewModel.userName.collectLatest { name ->
                binding.userName.text = name
            }
        }
    }

    private fun navigateTo(actionId: Int) {
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.navigate(actionId)
    }
}