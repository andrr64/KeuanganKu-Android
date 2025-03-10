package com.andreasoftware.keuanganku.ui.activity.intro
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.andreasoftware.keuanganku.data.repository.app.UserdataRepository
import com.andreasoftware.keuanganku.common.DataOperationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroPage2ViewModel @Inject constructor(
    private val userdataRepository: UserdataRepository
) : ViewModel() {

    fun saveNewName(name: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result: DataOperationResult = userdataRepository.saveNewUsername(name)
            onResult(result.success, result.errorMessage)
        }
    }
}
