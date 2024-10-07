package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components.numberinput

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NumberInputButton(
    modifier: Modifier = Modifier,
    withBorder: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val paddingBetween = 4.dp
    val innerPadding = 16.dp

    if (withBorder) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.padding(paddingBetween),
            contentPadding = PaddingValues(innerPadding)
        ) {
            content()
        }
    } else {
        TextButton(
            onClick = onClick,
            modifier = modifier.padding(paddingBetween),
            contentPadding = PaddingValues(innerPadding)
        ) {
            content()
        }
    }
}

@Composable
fun NumberInputButton(
    label: String,
    modifier: Modifier = Modifier,
    withBorder: Boolean = true,
    onClick: () -> Unit = {}
) {
    NumberInputButton(
        modifier = modifier,
        withBorder = withBorder,
        onClick = onClick
    ) {
        Text(text = label)
    }
}