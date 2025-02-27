package com.andreasoftware.keuanganku.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreasoftware.keuanganku.ui.activity.introduction.fragment.IntroductionPage1
import com.andreasoftware.keuanganku.ui.activity.introduction.fragment.IntroductionPage2

class IntroductionViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IntroductionPage1()
            1 -> IntroductionPage2()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}