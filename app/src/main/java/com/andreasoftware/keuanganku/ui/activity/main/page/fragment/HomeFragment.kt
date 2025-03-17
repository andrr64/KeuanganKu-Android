package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.enm.SortTransaction
import com.andreasoftware.keuanganku.common.enm.TimePeriod
import com.andreasoftware.keuanganku.databinding.FragmentHomeBinding
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.adapter.TransactionItemAdapter
import com.andreasoftware.keuanganku.util.CurrencyFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var transactionAdapter: TransactionItemAdapter
    private val userLocale: Locale = Locale("id", "ID")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("HomeFragment", "onCreateView called")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("HomeFragment", "onDestroy called")
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerExpensePeriod.spinnerLayout.hint = getString(R.string.period)
        binding.spinnerIncomePeriod.spinnerLayout.hint = getString(R.string.period)
        binding.spinnerRecentTransactionsPeriod.spinnerLayout.hint = getString(R.string.period)

        ///TODO: use string resource instead
        binding.spinnerSortTransactionsBy.spinnerLayout.hint = "Sort By"

        setupRecyclerView()
        setupListener()
        setupPeriodSpinner()
        setupObserver()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionItemAdapter(emptyList(), viewModel.getCategoryRepository(), userLocale) // Inisialisasi dengan list kosong
        binding.recyclerViewOfRecentTransactions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionAdapter
        }
    }

    private fun setupListener() {
        binding.buttonAddWallet.button.setOnClickListener {
            navigateTo(MainActivity.ACTION_MAIN_TO_WALLET_FORM)
        }
    }

    private fun setupPeriodSpinner() {
        val periods = TimePeriod.entries.map { it.displayName }
        val sortTransaction = SortTransaction.entries.map { it.displayName }
        val adapterSort = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, sortTransaction)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, periods)
        Log.d("SortTransaction", "Items: $sortTransaction")

        binding.spinnerSortTransactionsBy.spinner.setAdapter(adapterSort)
        binding.spinnerExpensePeriod.spinner.setAdapter(adapter)
        binding.spinnerIncomePeriod.spinner.setAdapter(adapter)
        binding.spinnerRecentTransactionsPeriod.spinner.setAdapter(adapter)

        binding.spinnerExpensePeriod.spinner.setText(viewModel.expensePeriod.value.displayName,false)
        binding.spinnerIncomePeriod.spinner.setText(viewModel.incomePeriod.value.displayName, false)
        binding.spinnerRecentTransactionsPeriod.spinner.setText(viewModel.incomePeriod.value.displayName, false)
        binding.spinnerSortTransactionsBy.spinner.setText(viewModel.sortTransaction.value.displayName, false)

        binding.spinnerExpensePeriod.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setExpensePeriod(TimePeriod.entries[position])
        }
        binding.spinnerIncomePeriod.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setIncomePeriod(TimePeriod.entries[position])
        }
        binding.spinnerRecentTransactionsPeriod.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setTransactionPeriod(TimePeriod.entries[position])
        }
        binding.spinnerSortTransactionsBy.spinner.setOnItemClickListener { _, _, position, _ ->
            viewModel.setSortTransaction(SortTransaction.entries[position])
        }
    }

    private fun setupObserver() {
        viewModel.balance.observe(viewLifecycleOwner) { balance ->
            binding.totalBalanceTextView.text = CurrencyFormatter.formatCurrency(balance, userLocale)
        }
        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.expenseAmount.text = CurrencyFormatter.formatCurrency(expense, userLocale)
        }
        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.incomeAmount.text = CurrencyFormatter.formatCurrency(income, userLocale)
        }
        lifecycleScope.launch {
            viewModel.expensePeriod.collectLatest {
                binding.expensePeriod.text = it.displayName
                binding.spinnerExpensePeriod.spinner.setText(it.displayName, false)
            }
        }
        lifecycleScope.launch {
            viewModel.sortTransaction.collectLatest {
                binding.spinnerSortTransactionsBy.spinner.setText(it.displayName, false)
            }
        }
        lifecycleScope.launch {
            viewModel.userName.collectLatest { name ->
                binding.userName.text = name
            }
        }
        lifecycleScope.launch {
            viewModel.transactionPeriod.collectLatest {
                binding.spinnerRecentTransactionsPeriod.spinner.setText(it.displayName, false)
            }
        }
        lifecycleScope.launch {
            viewModel.incomePeriod.collectLatest {
                binding.incomePeriod.text = it.displayName
                binding.spinnerIncomePeriod.spinner.setText(it.displayName, false)
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
        viewModel.recentTransactions.observe(viewLifecycleOwner) { transactions ->
            if (transactions != null) {
                transactionAdapter.updateTransactions(transactions)
                binding.recentTransactionEmptyTextview.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
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