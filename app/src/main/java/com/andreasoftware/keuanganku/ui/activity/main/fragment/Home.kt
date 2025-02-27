package com.andreasoftware.keuanganku.ui.activity.main.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.adapter.ShortTransactionsAdapter
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.common.enums.TransactionType
import com.andreasoftware.keuanganku.data.dataclass.ShortTransactionItem
import com.andreasoftware.keuanganku.databinding.FragmentAppMainmenuHomeBinding
import com.andreasoftware.keuanganku.ui.utils.StringFormatter
import com.andreasoftware.keuanganku.ui.activity.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {
    private var _binding: FragmentAppMainmenuHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppMainmenuHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Home", "onViewCreated: Home")
        super.onViewCreated(view, savedInstanceState)


        ///TODO: handle name
        binding.balanceCard.userName.text = "John"
        setupRvListTransaction()
        setupObservers()
        setupClickListeners()
        setupPeriodSpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRvListTransaction() {
        binding.listTransaction.rvTransactions.layoutManager = LinearLayoutManager(context)
        val adapter = ShortTransactionsAdapter()
        binding.listTransaction.rvTransactions.adapter = adapter
        viewModel.allIncomes.observe(viewLifecycleOwner) { incomes ->
            adapter.submitList(incomes) // Gunakan submitList() untuk memperbarui data
        }
    }

    private fun setupClickListeners() {
        binding.expenseCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_EXPENSE_FORM) }
        binding.incomeCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_INCOME_FORM) }
        binding.balanceCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_WALLET_FORM) }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.totalBalance.observe(viewLifecycleOwner) {
                binding.balanceCard.totalBalanceTextView.text =
                    StringFormatter.formatNumber(it ?: 0.0)
            }

            viewModel.expensePeriod.observe(viewLifecycleOwner) { selectedPeriod ->
                binding.expenseCard.spinnerPeriod.spinner.setText(selectedPeriod, false)
            }

            viewModel.incomePeriod.observe(viewLifecycleOwner) { selectedPeriod ->
                binding.incomeCard.spinnerPeriod.spinner.setText(selectedPeriod, false)
            }

            viewModel.incomeTotal.observe(viewLifecycleOwner) {
                binding.incomeCard.incomeAmount.text = StringFormatter.formatNumber(it ?: 0.0)
            }

            viewModel.expenseTotal.observe(viewLifecycleOwner) {
                binding.expenseCard.expenseAmount.text = StringFormatter.formatNumber(it ?: 0.0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.username.collectLatest {
                binding.balanceCard.userName.text = it
            }
        }
    }

    private fun setupPeriodSpinner() {
        val periodLabels = PeriodOptions.entries.map { it.label }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            periodLabels
        )

        binding.expenseCard.spinnerPeriod.spinnerLayout.hint = ""
        binding.expenseCard.spinnerPeriod.spinner.apply {
            setAdapter(adapter)
            setText(viewModel.expensePeriod.value, false)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedItem = periodLabels[position]
                viewModel.setExpensePeriod(selectedItem)
            }
        }

        // Setup income spinner
        binding.incomeCard.spinnerPeriod.spinnerLayout.hint = ""
        binding.incomeCard.spinnerPeriod.spinner.apply {
            setAdapter(adapter)
            setText(viewModel.incomePeriod.value, false)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedItem = periodLabels[position]
                viewModel.setIncomePeriod(selectedItem)
            }
        }
    }

    private fun navigateTo(actionId: Int) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.root_nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController
        navController?.navigate(actionId)
        Log.d("Home", "navigateTo: $navController")
    }

    companion object {
        private val ACTION_INCOME_FORM = R.id.action_main_fragment_to_incomeForm
        private val ACTION_EXPENSE_FORM = R.id.action_main_fragment_to_expenseForm
        private val ACTION_WALLET_FORM = R.id.action_main_fragment_to_walletForm
    }
}