package com.andreasoftware.keuanganku.ui.activity.introduction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.adapter.IntroductionViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        viewPager = findViewById(R.id.viewPager)
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager.adapter = IntroductionViewPagerAdapter(this)
    }
}