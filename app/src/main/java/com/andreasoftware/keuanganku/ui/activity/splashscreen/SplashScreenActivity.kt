package com.andreasoftware.keuanganku.ui.activity.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.ui.activity.introduction.IntroductionActivity
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.data.datastore.UserDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint // Tambahkan ini
class SplashScreenActivity : AppCompatActivity() {

    @Inject // Tambahkan ini
    lateinit var userDataStore: UserDataStore

    init {
        Log.d("SplashScreenActivity.kt", "init: Init Splash Screen")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        CoroutineScope(Dispatchers.Main).launch {
            delay(250) // simulasi loading data

            if (checkData()) {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            } else {
                startActivity(Intent(this@SplashScreenActivity, IntroductionActivity::class.java))
            }

            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SplashScreenActivity.kt", "onDestroy: Destroy Splash Screen")
    }

    private fun checkData(): Boolean {
        return runBlocking {
            userDataStore.isUsernameExist.first()
        }
    }
}