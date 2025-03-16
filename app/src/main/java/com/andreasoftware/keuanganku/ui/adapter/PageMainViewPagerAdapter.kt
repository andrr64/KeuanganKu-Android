package com.andreasoftware.keuanganku.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreasoftware.keuanganku.ui.activity.main.page.fragment.AnalysisFragment
import com.andreasoftware.keuanganku.ui.activity.main.page.fragment.HomeFragment
import com.andreasoftware.keuanganku.ui.activity.main.page.fragment.ToPayFragment
import com.andreasoftware.keuanganku.ui.activity.main.page.fragment.WalletFragment

class PageMainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> WalletFragment()
            2 -> AnalysisFragment()
            3 -> ToPayFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int = 4

}