package com.andreasoftware.keuanganku.ui.activity.introduction.viewmodel

import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.datastore.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroPage2ViewModel @Inject constructor(
    userDataStore: UserDataStore
) : ViewModel() {
    val userDataStore = userDataStore
}