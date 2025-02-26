package com.andreasoftware.keuanganku.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.R

class RootViewModel : ViewModel() {
    private val _fragmentId = MutableLiveData(R.id.homeMenuId)
    val bottomNavbarValue: LiveData<Int> = _fragmentId

    fun setFragmentId(id: Int) {
        _fragmentId.value = id
    }
}