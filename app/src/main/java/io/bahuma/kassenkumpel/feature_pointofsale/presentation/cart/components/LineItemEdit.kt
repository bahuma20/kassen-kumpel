package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components.number_change_input.NumberChangeInput
import io.bahuma.kassenkumpel.utils.formatPrice

@Composable
fun LineItemEdit(
    name: String,
    price: Double,
    count: Int,
    onCountChanged: (Int) -> Unit,
    onRemoveItem: () -> Unit,
    onClose: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val spaceBetweenFields = 16.dp

            val sum = rememberSaveable(price, count) { price * count }

            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(Modifier.height(spaceBetweenFields))

            NumberChangeInput(
                value = count,
                onValueChange = onCountChanged,
                modifier = Modifier.widthIn(Dp.Unspecified, 200.dp)
            )

            Spacer(Modifier.height(spaceBetweenFields))

            Text(
                text = "Gesamtpreis"
            )
            Text(
                text = formatPrice(sum),
                fontSize = 20.sp
            )

            Spacer(Modifier.height(spaceBetweenFields))

            Row {
                TextButton(
                    onClick = onRemoveItem,
                    colors = ButtonDefaults.textButtonColors().copy(contentColor = Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Löschen"
                    )
                    Text("Artikel entfernen")
                }

                Spacer(Modifier.weight(1f))

                Button(onClick = onClose) {
                    Text("Speichern")
                }
            }
        }

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(Icons.Default.Close, "Schließen")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemEditPreview() {
    LineItemEdit("Breze", 1.5, 5, {}, {})
}