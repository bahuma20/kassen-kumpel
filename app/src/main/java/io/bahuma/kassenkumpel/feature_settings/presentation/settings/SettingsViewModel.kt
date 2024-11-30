package io.bahuma.kassenkumpel.feature_settings.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sumup.merchant.reader.api.SumUpAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = mutableStateOf(SettingsState())
    val uiState: State<SettingsState> = _uiState

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        _uiState.value = uiState.value.copy(
            loggedIn = SumUpAPI.isLoggedIn(),
            merchantCode = SumUpAPI.getCurrentMerchant()?.merchantCode
        )
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.Logout -> {
                SumUpAPI.logout()
                _uiState.value = uiState.value.copy(loggedIn = false, merchantCode = null)
            }
        }
    }

}