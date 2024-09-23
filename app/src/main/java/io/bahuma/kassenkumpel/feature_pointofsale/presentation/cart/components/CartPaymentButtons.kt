package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartPaymentButtons(
    onPayCash: () -> Unit,
    onPayCard: () -> Unit,
    onPayLater: () -> Unit,

    ) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        val contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp)
        val buttonModifier = Modifier
        val buttonFontSize = 16.sp

        FilledTonalButton(
            onClick = onPayLater,
            contentPadding = contentPadding,
            modifier = buttonModifier
        ) {
            Text(
                text = "Anschreiben",
                fontSize = buttonFontSize
            )
        }

        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onPayCash,
            contentPadding = contentPadding,
            modifier = buttonModifier.weight(1f)
        ) {
            Text(
                text = "Bar",
                fontSize = buttonFontSize
            )
        }

        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onPayCard,
            contentPadding = contentPadding,
            modifier = buttonModifier.weight(1f)
        ) {
            Text(
                text = "Karte",
                fontSize = buttonFontSize
            )
        }
    }
}