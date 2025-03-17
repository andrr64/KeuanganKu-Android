package com.andreasoftware.keuanganku.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        Log.d("MainActivity.kt", "Created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity.kt", "onDestroy called")
    }
}