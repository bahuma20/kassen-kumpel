package io.bahuma.kassenkumpel.feature_settings.presentation.settings

data class SettingsState(
    val loggedIn: Boolean = false,
    val merchantCode: String? = null,
)
