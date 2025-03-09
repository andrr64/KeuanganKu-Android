package com.andreasoftware.keuanganku.ui.activity.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.repository.app.SettingRepository
import com.andreasoftware.keuanganku.databinding.ActivityIntroBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewPager: ViewPager2

    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        viewPager = binding.viewPager

        lifecycleScope.launch {
            settingRepository.settingDS.saveLanguageLocale("en")
        }

        setContentView(binding.root)
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager.adapter = IntroPageViewpageAdapter(this)
    }
}
