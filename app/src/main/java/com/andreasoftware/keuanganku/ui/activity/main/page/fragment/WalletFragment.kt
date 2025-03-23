package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentWalletBinding
import com.andreasoftware.keuanganku.ui.activity.main.MainActivityNavigator
import com.andreasoftware.keuanganku.ui.adapter.ExpenseLimiterItemAdapter
import com.andreasoftware.keuanganku.ui.adapter.WalletItemAdapter
import com.andreasoftware.keuanganku.ui.common.AppSnackBar
import com.andreasoftware.keuanganku.ui.modal.WalletFormBSFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private var _binding: FragmentWalletBinding? = null // Corrected type here
    private val binding get() = _binding!!
    private lateinit var walletItemsAdapter: WalletItemAdapter
    private lateinit var expenseLimiterAdapter: ExpenseLimiterItemAdapter

    private val viewModel: WalletFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
        binding.tvEmptyExpenseLimiter.textview.setTextColor(getColor(requireContext(), R.color.white))
        binding.tvEmptyWallet.textview.setTextColor(getColor(requireContext(), R.color.white))
        return binding.root // Corrected return statement
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupObserver()
        setupListener()
    }

    fun setupAdapter(){
        ///TODO: change locale
        ///TODO: handle on click listener
        walletItemsAdapter = WalletItemAdapter(emptyList(), Locale("id", "ID"), onItemClick = { wallet ->
            MainActivityNavigator.navigateFromMainToDetailWallet(requireActivity(), wallet)
            Log.d("WalletFragment", "Wallet clicked: $wallet")
        })
        expenseLimiterAdapter = ExpenseLimiterItemAdapter(emptyList(),viewModel.categoryRepository, Locale("id", "ID"), onItemClick = { limiter ->})

        binding.walletsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = walletItemsAdapter
        }
        binding.expenseLimiterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = expenseLimiterAdapter
        }
    }

    fun setupObserver(){
        viewModel.wallets.observe(viewLifecycleOwner) { wallets ->
            walletItemsAdapter.updateWallet(wallets)
            binding.tvEmptyWallet.textview.visibility = if (wallets.isEmpty()) View.VISIBLE else View.GONE
        }
        viewModel.expenseLimiters.observe(viewLifecycleOwner) {
            expenseLimiterAdapter.updateExpenseLimiters(it)
            binding.tvEmptyExpenseLimiter.textview.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    fun setupListener(){
        binding.buttonAddWallet.button.setOnClickListener {
            WalletFormBSFragment().show(childFragmentManager, "WalletBottomSheetDialogFragment")
        }
        binding.buttonAddSpendingLimiter.button.setOnClickListener {
            if (viewModel.wallets.value!!.isEmpty()){
                AppSnackBar.error(binding.root, "Please add wallet first")
            } else {
                MainActivityNavigator.navigateFromMainToExpenseLimiterForm(requireActivity())
            }
        }
    }
}