package io.bahuma.kassenkumpel.feature_settings.presentation.settings

import io.bahuma.kassenkumpel.core.controller.SnackbarMessage

data class SettingsState(
    val loggedIn: Boolean = false,
    val merchantCode: String? = null,
    val confirmDeleteTransactionsDialogOpen: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null
)
