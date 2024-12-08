package io.bahuma.kassenkumpel.feature_settings.presentation.settings

sealed class SettingsEvent {
    data object Logout : SettingsEvent()
    data object DeleteTransactions : SettingsEvent()
    data object DeleteTransactionsConfirm : SettingsEvent()
    data object DeleteTransactionsCancel : SettingsEvent()
    data object SnackbarCloseEvent : SettingsEvent()
}