package io.bahuma.kassenkumpel.feature_settings.presentation.settings

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumup.merchant.reader.api.SumUpAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.core.controller.SnackbarMessage
import io.bahuma.kassenkumpel.core.controller.UserMessage
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.TransactionUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
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

            SettingsEvent.DeleteTransactions -> {
                _uiState.value = uiState.value.copy(confirmDeleteTransactionsDialogOpen = true)
            }

            SettingsEvent.DeleteTransactionsCancel -> {
                _uiState.value = uiState.value.copy(confirmDeleteTransactionsDialogOpen = false)
            }

            SettingsEvent.DeleteTransactionsConfirm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val numberDeleted = transactionUseCases.deleteAllTransactions()
                    _uiState.value = uiState.value.copy(
                        confirmDeleteTransactionsDialogOpen = false,
                        snackbarMessage = SnackbarMessage.from(
                            userMessage = UserMessage.from("$numberDeleted Transaktionen wurden gelöscht"),
                            withDismissAction = false,
                            duration = SnackbarDuration.Short
                        )
                    )
                }
            }

            SettingsEvent.SnackbarCloseEvent -> {
                _uiState.value = uiState.value.copy(snackbarMessage = null)
            }
        }
    }

}