package com.andreasoftware.keuanganku.ui.activity.main.page.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreasoftware.keuanganku.databinding.FragmentWalletBinding
import com.andreasoftware.keuanganku.ui.activity.main.MainActivityNavigator
import com.andreasoftware.keuanganku.ui.adapter.WalletItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class WalletFragment : Fragment() {
    private var _binding: FragmentWalletBinding? = null // Corrected type here
    private val binding get() = _binding!!
    private lateinit var walletItemsAdapter: WalletItemAdapter
    private val viewModel: WalletFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWalletBinding.inflate(inflater, container, false)
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
        walletItemsAdapter = WalletItemAdapter(emptyList(), Locale("id", "ID"), onItemClick = { wallet ->
            MainActivityNavigator.navigateFromMainToDetailWallet(requireActivity(), wallet)
            Log.d("WalletFragment", "Wallet clicked: $wallet")
        })
        binding.walletsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = walletItemsAdapter
        }
    }

    fun setupObserver(){
        viewModel.wallets.observe(viewLifecycleOwner) { wallets ->
            walletItemsAdapter.updateWallet(wallets)
        }
    }

    fun setupListener(){
        binding.buttonAddWallet.button.setOnClickListener {
            MainActivityNavigator.navigateFromMainToWalletForm(requireActivity())
        }
    }
}