package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.utils.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    lineItems: List<LineItem>,
    modifier: Modifier = Modifier,
    onRemove: (LineItem) -> Unit = {},
    onPayCash: (Double) -> Unit = {},
    onPayCard: (Double) -> Unit = {},
    onPayLater: (Double) -> Unit = {},
    onClearCart: () -> Unit = {},
) {
    val gesamtbetrag by remember {
        derivedStateOf {
            lineItems.map { it.amount * it.pricePerUnit }.fold(0.0) { acc, value -> acc + value }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
            .padding(16.dp)
    ) {
        Row {
            TopAppBar(
                title = { Text("Warenkorb") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add custom item"
                        )
                    }

                    IconButton(onClick = { onClearCart() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove all items"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }

        CartItemList(
            lineItems,
            onRemove,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )

        if (lineItems.isNotEmpty()) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Gesamtbetrag",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Text(
                    text = formatPrice(gesamtbetrag),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            CartPaymentButtons(
                onPayCard = { onPayCard(gesamtbetrag) },
                onPayCash = { onPayCash(gesamtbetrag) },
                onPayLater = { onPayLater(gesamtbetrag) }
            )
        }
    }
}

@Preview
@Composable
fun CartPreview() {
    val lineItems = listOf(
        LineItem("Kaffee", 2.5, 1, 1),
        LineItem("Kuchen", 3.0, 10, 2),
    )

    Cart(lineItems)
}