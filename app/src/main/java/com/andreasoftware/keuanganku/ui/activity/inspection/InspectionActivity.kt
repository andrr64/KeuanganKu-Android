package com.andreasoftware.keuanganku.ui.activity.inspection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andreasoftware.keuanganku.ui.activity.main.MainActivity
import com.andreasoftware.keuanganku.R
import com.andreasoftware.keuanganku.data.repository.app.UserdataRepository
import com.andreasoftware.keuanganku.ui.activity.intro.IntroActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InspectionActivity : AppCompatActivity() {

    @Inject
    lateinit var userdataRepository: UserdataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspection)

        CoroutineScope(Dispatchers.Main).launch {
            delay(250)
            val hasUsername = checkData()
            val nextActivity = if (hasUsername) MainActivity::class.java else IntroActivity::class.java
            startActivity(Intent(this@InspectionActivity, nextActivity))
            finish()
        }
    }

    private suspend fun checkData(): Boolean {
        return userdataRepository.isUsernameExists()
    }
}
