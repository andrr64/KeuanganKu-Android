package com.andreasoftware.keuanganku.ui.activity.main.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.PageMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageMain : Fragment() {
    private var _binding: PageMainBinding? = null
    private lateinit var drawerLayout: DrawerLayout
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PageMainBinding.inflate(inflater, container, false)
        drawerLayout = binding.drawerLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupBottomNavigation()
        setupDrawer()
        Log.d("MainPage.kt", "Created...")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("MainPage.kt", "Destroyed..")
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = PageMainVPagerAdapter(requireActivity())
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavbar.menu[position].isChecked = true
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavbar.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavbar.setOnItemSelectedListener { item ->
            val position = when (item.itemId) {
                MENU_HOME -> 0
                MENU_WALLET -> 1
                MENU_ANALYSIS -> 2
                MENU_TOPAY -> 3
                else -> return@setOnItemSelectedListener false
            }
            binding.viewPager.currentItem = position
            true
        }
    }

    private fun setupDrawer() {
        binding.appBar.appBarDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val MENU_HOME = R.id.homeMenuId
        private val MENU_WALLET = R.id.walletMenuId
        private val MENU_ANALYSIS = R.id.analysisMenuId
        private val MENU_TOPAY = R.id.toPayMenuId
    }
}