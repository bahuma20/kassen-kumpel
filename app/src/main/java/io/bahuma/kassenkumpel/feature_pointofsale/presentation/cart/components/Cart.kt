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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.utils.formatPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cart(
    lineItems: List<LineItem>,
    totalAmount: Double,
    cardPaymentAvailable: Boolean = false,
    onRemoveLineItem: (LineItem) -> Unit,
    onChangeLineItemAmount: (LineItem, Int) -> Unit,
    modifier: Modifier = Modifier,
    onPayCash: (Double) -> Unit = {},
    onPayCard: (Double) -> Unit = {},
    onPayLater: (Double) -> Unit = {},
    onClearCart: () -> Unit = {},
) {
    var editedLineItem: LineItem? by remember { mutableStateOf(null) }

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
//                    IconButton(onClick = {}) {
//                        Icon(
//                            imageVector = Icons.Filled.AddCircle,
//                            contentDescription = "Add custom item"
//                        )
//                    }

                    IconButton(onClick = { onClearCart() }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Remove all items"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        }

        CartItemList(
            lineItems = lineItems,
            onRemove = onRemoveLineItem,
            onClickLineItem = { editedLineItem = it },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        )

        if (lineItems.isNotEmpty()) {

            Spacer(Modifier.height(16.dp))

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
                    text = formatPrice(totalAmount),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.height(24.dp))

            CartPaymentButtons(
                onPayCard = { onPayCard(totalAmount) },
                onPayCash = { onPayCash(totalAmount) },
                onPayLater = { onPayLater(totalAmount) },
                cardPaymentEnabled = totalAmount > 0 && cardPaymentAvailable,
            )
        }
    }

    if (editedLineItem != null) {
        Dialog(onDismissRequest = {
            editedLineItem = null
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors().copy(containerColor = Color.White)
            ) {
                LineItemEdit(
                    name = editedLineItem!!.name,
                    price = editedLineItem!!.pricePerUnit,
                    count = editedLineItem!!.amount,
                    onCountChanged = {
                        onChangeLineItemAmount(editedLineItem!!, it)
                        editedLineItem = editedLineItem!!.copy(amount = it)
                    },
                    onRemoveItem = {
                        onRemoveLineItem(editedLineItem!!)
                        editedLineItem = null
                    },
                    onClose = { editedLineItem = null },
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun CartPreview() {
    val lineItems = listOf(
        LineItem("Kaffee", 2.5, 1, 1, null),
        LineItem("Kuchen", 3.0, 10, 2, null),
    )

    Cart(lineItems, 12.50, true, {}, { _, _ -> })
}