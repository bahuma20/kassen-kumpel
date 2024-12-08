package io.bahuma.kassenkumpel.feature_settings.presentation.settings

import android.content.Context

sealed class SettingsEvent {
    data object Logout : SettingsEvent()
    data object DeleteTransactions : SettingsEvent()
    data object DeleteTransactionsConfirm : SettingsEvent()
    data object DeleteTransactionsCancel : SettingsEvent()
    data object SnackbarCloseEvent : SettingsEvent()
    class ExportTransactions(val context: Context) : SettingsEvent()
}