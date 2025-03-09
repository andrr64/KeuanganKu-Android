package com.andreasoftware.keuanganku.ui.activity.main

import android.os.Bundle
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
    }

    companion object {
        val ACTION_MAIN_TO_WALLET_FORM = R.id.action_main_fragment_to_walletForm
        val ACTION_MAIN_TO_EXPENSE_FORM = R.id.action_main_fragment_to_expenseForm
        val ACTION_MAIN_TO_INCOME_FORM = R.id.action_main_fragment_to_incomeForm
    }
}