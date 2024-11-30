package io.bahuma.kassenkumpel.feature_settings.presentation.settings

sealed class SettingsEvent {
    data object Logout : SettingsEvent()
}