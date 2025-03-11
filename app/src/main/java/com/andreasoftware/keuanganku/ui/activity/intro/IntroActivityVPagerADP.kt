package com.andreasoftware.keuanganku.ui.activity.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreasoftware.keuanganku.ui.activity.intro.page.PageIntro1
import com.andreasoftware.keuanganku.ui.activity.intro.page.PageIntro2

class IntroActivityVPagerADP(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PageIntro1()
            1 -> PageIntro2()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getItemCount(): Int = 2
}