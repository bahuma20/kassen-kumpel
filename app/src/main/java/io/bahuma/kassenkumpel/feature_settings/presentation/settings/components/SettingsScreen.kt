package io.bahuma.kassenkumpel.feature_settings.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.bahuma.kassenkumpel.R
import io.bahuma.kassenkumpel.core.controller.SnackbarMessageHandler
import io.bahuma.kassenkumpel.feature_settings.presentation.settings.SettingsEvent
import io.bahuma.kassenkumpel.feature_settings.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState

    val context = LocalContext.current

    SnackbarMessageHandler(
        snackbarMessage = uiState.value.snackbarMessage,
        { viewModel.onEvent(SettingsEvent.SnackbarCloseEvent) })

    Column {
        if (uiState.value.merchantCode != null) {
            Text("Eingeloggt als ${uiState.value.merchantCode}")
        }

        Spacer(Modifier.height(8.dp))

        if (uiState.value.loggedIn) {
            Button(onClick = {
                viewModel.onEvent(SettingsEvent.Logout)
            }) {
                Text("Logout")
            }
        } else {
            Text("Not logged in")
        }

        Spacer(Modifier.height(8.dp))

        Text("Environment: " + stringResource(R.string.environment))

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.onEvent(SettingsEvent.DeleteTransactions)
        }) {
            Text("Delete all transactions")
        }

        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = {
                viewModel.onEvent(SettingsEvent.ExportTransactions(context))
            }) {
                Text("Transaktionen als CSV exportieren")
            }
        }

    }

    if (uiState.value.confirmDeleteTransactionsDialogOpen) {
        AlertDialog(
            title = {
                Text(text = "Möchten Sie fortfahren?")
            },
            text = {
                Text(text = "Alle bestehenden Transaktionen werden unwiderruflich gelöscht.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(SettingsEvent.DeleteTransactionsConfirm)
                    }
                ) {
                    Text("Daten löschen")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(SettingsEvent.DeleteTransactionsCancel)
                    }
                ) {
                    Text("Abbrechen")
                }
            },
            onDismissRequest = {
                viewModel.onEvent(SettingsEvent.DeleteTransactionsCancel)
            }
        )
    }


}