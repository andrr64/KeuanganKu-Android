package com.andreasoftware.keuanganku.ui.activity.intro.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreasoftware.keuanganku.common.cls.DataOperationResult
import com.andreasoftware.keuanganku.data.repository.app.UserdataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PageIntro2ViewModel @Inject constructor(
    private val userdataRepository: UserdataRepository
) : ViewModel() {

    fun saveNewName(name: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result: DataOperationResult = userdataRepository.saveNewUsername(name)
            onResult(result.success, result.errorMessage)
        }
    }
}
