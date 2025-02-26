package com.andreasoftware.keuanganku.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentAppRootBinding
import com.andreasoftware.keuanganku.ui.viewmodels.RootViewModel

class Root : Fragment() {

    private lateinit var binding: FragmentAppRootBinding
    private lateinit var drawerLayout: DrawerLayout
    private val viewModel: RootViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerLayout = binding.drawerLayout
        setupBottomNavigation()
        setupNavigationDrawer()
        observeBottomNavValue()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavbar.setOnItemSelectedListener { item ->
            viewModel.setFragmentId(
                when (item.itemId) {
                    MENU_HOME -> MENU_HOME
                    MENU_EXPENSES -> MENU_EXPENSES
                    else -> return@setOnItemSelectedListener false
                }
            )
            true
        }
    }

    private fun setupNavigationDrawer() {
        binding.appBar.appBarDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun changeFragment(fragment: Fragment) {
        parentFragmentManager.commit {
            replace(R.id.frameLayout, fragment)
        }
    }

    private fun observeBottomNavValue() {
        viewModel.bottomNavbarValue.observe(viewLifecycleOwner) { fragmentId ->
            when (fragmentId) {
                MENU_HOME -> changeFragment(Home())
                MENU_EXPENSES -> changeFragment(Expenses())
            }
        }
    }

    companion object {
        private val MENU_HOME = R.id.homeMenuId
        private val MENU_EXPENSES = R.id.expenseMenuId
    }
}