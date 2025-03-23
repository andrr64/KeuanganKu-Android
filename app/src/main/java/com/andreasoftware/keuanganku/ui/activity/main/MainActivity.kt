package com.andreasoftware.keuanganku.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.andreasoftware.keuanganku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        enableEdgeToEdge()
        val controller = WindowCompat.getInsetsController(window, window.decorView)

        controller.isAppearanceLightStatusBars = true  // Ikon status bar gelap
        controller.isAppearanceLightNavigationBars = true  // Ikon navigasi gelap
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity.kt", "onDestroy called")
    }
}