package com.andreasoftware.keuanganku.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.FragmentAppMainBinding

class Main : Fragment() {

    private lateinit var binding: FragmentAppMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawerLayout = binding.drawerLayout
        binding.viewPager.adapter = MainViewPagerAdapter(requireActivity())

        setupBottomNavigation()
        setupNavigationDrawer()
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavbar.menu.getItem(position).isChecked = true
            }
        })
    }

    private fun setupBottomNavigation() {
        binding.bottomNavbar.setOnItemSelectedListener { item ->
            val position = when (item.itemId) {
                MENU_HOME -> 0
                MENU_EXPENSES -> 1
                else -> return@setOnItemSelectedListener false
            }
            binding.viewPager.currentItem = position
            true
        }
    }

    private fun setupNavigationDrawer() {
        binding.appBar.appBarDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    companion object {
        private val MENU_HOME = R.id.homeMenuId
        private val MENU_EXPENSES = R.id.expenseMenuId
    }
}