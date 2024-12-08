package io.bahuma.kassenkumpel.feature_settings.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.bahuma.kassenkumpel.R
import io.bahuma.kassenkumpel.feature_settings.presentation.settings.SettingsEvent
import io.bahuma.kassenkumpel.feature_settings.presentation.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState

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
            Text("Nicht eingeloggt")
        }

        Spacer(Modifier.height(8.dp))

        Text("Environment: " + stringResource(R.string.environment))
    }

}