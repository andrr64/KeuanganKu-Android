package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.databinding.FragmentHomeBinding
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        setupListener()
        setupExpensePeriodSpinner()
        setupObserver()
    }

    private fun setupListener() {
        binding.buttonAddWallet.button.setOnClickListener {
            navigateTo(MainActivity.ACTION_MAIN_TO_WALLET_FORM)
        }
    }

    private fun setupExpensePeriodSpinner() {
        val periods = TimePeriod.entries.map { it.displayName }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, periods)
        binding.spinnerExpensePeriod.spinner.setAdapter(adapter)
        binding.spinnerIncomePeriod.spinner.setAdapter(adapter)

        binding.spinnerExpensePeriod.spinner.setText(
            viewModel.expensePeriod.value.displayName,
            false
        )
        binding.spinnerIncomePeriod.spinner.setText(viewModel.incomePeriod.value.displayName, false)

        binding.spinnerExpensePeriod.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setExpensePeriod(TimePeriod.entries[position])
            binding.spinnerExpensePeriod.spinner.setText(periods[position], false)
        }
        binding.spinnerIncomePeriod.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setIncomePeriod(TimePeriod.entries[position])
            binding.spinnerIncomePeriod.spinner.setText(periods[position], false)
        }
    }

    private fun setupObserver() {
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.totalBalanceTextView.text = "IDR $balance"
        }
        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.expenseAmount.text = "IDR $expense"
        }
        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.incomeAmount.text = "IDR $income"
        }
        lifecycleScope.launch {
            viewModel.expensePeriod.collectLatest {
                binding.expensePeriod.text = it.displayName
            }
        }
        lifecycleScope.launch {
            viewModel.userName.collectLatest { name ->
                binding.userName.text = name
            }
        }
        lifecycleScope.launch {
            viewModel.incomePeriod.collectLatest {
                binding.incomePeriod.text = it.displayName
            }
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
                    AppSnackBar.error(binding.root, "Anda belum memiliki dompet!")
                }
                binding.buttonAddExpense.button.setOnClickListener {
                    AppSnackBar.error(binding.root, "Anda belum memiliki dompet!")
                }
            }
        }
    }

    private fun navigateTo(actionId: Int) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.navigate(actionId)
    }
}