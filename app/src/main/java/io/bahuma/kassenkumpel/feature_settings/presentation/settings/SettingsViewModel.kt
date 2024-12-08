package io.bahuma.kassenkumpel.feature_settings.presentation.settings

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumup.merchant.reader.api.SumUpAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.core.controller.SnackbarMessage
import io.bahuma.kassenkumpel.core.controller.UserMessage
import io.bahuma.kassenkumpel.feature_transactions.domain.exception.ExportTransactionsException
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.TransactionUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
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

            is SettingsEvent.ExportTransactions -> {
                viewModelScope.launch(Dispatchers.IO) {

                    val transactions =
                        transactionUseCases.getTransactionsWithTransactionLineItems().first()


                    val transactionsOutputStream =
                        getExportOutputStream(event.context, "kassenkumpel-transactions")
                    val transactionLineItemsOutputStream =
                        getExportOutputStream(event.context, "kassenkumpel-transaction-lineitems")

                    if (transactionsOutputStream == null || transactionLineItemsOutputStream == null) {
                        showExportErrorMessage()
                        return@launch
                    }

                    try {
                        transactionUseCases.exportTransactionsToCSV(
                            transactions,
                            transactionsOutputStream,
                            transactionLineItemsOutputStream
                        )

                        _uiState.value = _uiState.value.copy(
                            snackbarMessage = SnackbarMessage.from(
                                userMessage = UserMessage.from("CSVs wurden in ${Environment.DIRECTORY_DOCUMENTS}/KassenKumpel/export gespeichert."),
                                withDismissAction = false,
                                duration = SnackbarDuration.Short
                            )
                        )
                    } catch (e: ExportTransactionsException) {
                        showExportErrorMessage()
                        e.printStackTrace()
                    }
                }

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getExportOutputStream(context: Context, fileName: String): OutputStream? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")

        val values = ContentValues()
        values.put(
            MediaStore.MediaColumns.DISPLAY_NAME,
            fileName + "-" + dateFormat.format(Date.from(Instant.now()))
        )
        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
        values.put(
            MediaStore.MediaColumns.RELATIVE_PATH,
            Environment.DIRECTORY_DOCUMENTS + "/KassenKumpel/export"
        )

        val uri = context.contentResolver.insert(
            MediaStore.Files.getContentUri("external"),
            values
        )

        if (uri == null) {
            return null
        }

        return context.contentResolver.openOutputStream(uri)
    }

    private fun showExportErrorMessage() {
        _uiState.value = uiState.value.copy(
            snackbarMessage = SnackbarMessage.from(
                userMessage = UserMessage.from("Es gab einen Fehler beim Exportieren"),
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        )
    }

}