package com.andreasoftware.keuanganku.ui.fragments

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
import androidx.navigation.fragment.findNavController
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.common.enums.PeriodOptions
import com.andreasoftware.keuanganku.databinding.FragmentAppMainmenuHomeBinding
import com.andreasoftware.keuanganku.ui.utils.StringFormatter
import com.andreasoftware.keuanganku.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Home", "onViewCreated: Home")
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupClickListeners()
        setupPeriodSpinner()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.expenseCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_EXPENSE_FORM) }
        binding.incomeCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_INCOME_FORM) }
        binding.balanceCard.buttonAdd.button.setOnClickListener { navigateTo(ACTION_WALLET_FORM) }
    }

    @SuppressLint("SetTextI18n")
    private fun setupObservers() {
        viewModel.totalBalance.observe(viewLifecycleOwner) {
            binding.balanceCard.totalBalanceTextView.text = StringFormatter.formatNumber(it ?: 0.0)
            viewModel.updateIncomeTotal()
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
        findNavController().navigate(actionId)
    }

    companion object {
        private val ACTION_INCOME_FORM = R.id.action_main_fragment_to_incomeForm
        private val ACTION_EXPENSE_FORM = R.id.action_main_fragment_to_expenseForm
        private val ACTION_WALLET_FORM = R.id.action_main_fragment_to_walletForm
    }
}