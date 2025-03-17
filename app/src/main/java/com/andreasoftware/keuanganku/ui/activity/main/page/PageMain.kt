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
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.PageMainBinding
import com.andreasoftware.keuanganku.ui.adapter.PageMainViewPagerAdapter
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageMain : Fragment() {
    private var _binding: PageMainBinding? = null
    private lateinit var drawerLayout: DrawerLayout
    private val binding get() = _binding!!  // Gunakan safe binding untuk menghindari null pointer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PageMainBinding.inflate(inflater, container, false)
        drawerLayout = binding.drawerLayout  // Inisialisasi drawer layout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()      // Setup ViewPager2
        setupBottomNavigation()  // Setup Bottom Navigation
        setupDrawer()         // Setup Drawer Menu

        Log.d("MainPage.kt", "Created...")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Hindari memory leak dengan menghapus binding saat fragment dihancurkan
        Log.d("MainPage.kt", "Destroyed..")
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = PageMainViewPagerAdapter(requireActivity())
        binding.viewPager.setPageTransformer(MarginPageTransformer(10))
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavbar.menu[position].isChecked = true  // Sinkronisasi bottom navigation dengan ViewPager
            }
        })
        binding.viewPager.offscreenPageLimit = 4  // Simpan semua halaman agar tidak di-destroy saat berpindah
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
            binding.viewPager.setCurrentItem(position, false) // Pindah halaman tanpa animasi
            true
        }
    }

    private fun setupDrawer() {
        binding.appBar.appBarDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)  // Buka navigation drawer saat tombol ditekan
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null  // Pastikan binding dilepas untuk menghindari memory leak
    }

    companion object {
        private val MENU_HOME = R.id.homeMenuId
        private val MENU_WALLET = R.id.walletMenuId
        private val MENU_ANALYSIS = R.id.analysisMenuId
        private val MENU_TOPAY = R.id.toPayMenuId
    }
}
