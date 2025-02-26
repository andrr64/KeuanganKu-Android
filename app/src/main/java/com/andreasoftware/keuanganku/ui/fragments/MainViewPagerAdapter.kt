package com.andreasoftware.keuanganku.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreasoftware.keuanganku.ui.fragments.menu.Expenses
import com.andreasoftware.keuanganku.ui.fragments.menu.Home

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Home()
            1 -> Expenses()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}