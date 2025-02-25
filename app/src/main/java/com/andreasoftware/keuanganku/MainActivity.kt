package com.andreasoftware.keuanganku

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andreasoftware.keuanganku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }
}