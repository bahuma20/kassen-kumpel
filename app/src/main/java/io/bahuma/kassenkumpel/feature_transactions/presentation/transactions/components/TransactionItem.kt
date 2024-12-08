package io.bahuma.kassenkumpel.feature_transactions.presentation.transactions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import io.bahuma.kassenkumpel.utils.formatDate
import io.bahuma.kassenkumpel.utils.formatPrice

@Composable
fun TransactionItem(
    data: TransactionWithTransactionLineItems,
    modifier: Modifier = Modifier
) {
    val detailsOpen = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
            .clickable(
                onClick = {
                    detailsOpen.value = !detailsOpen.value
                }
            )
    ) {
        Row {
            Text(
                text = "#" + data.transaction.transactionId.toString(),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = formatDate(data.transaction.timestamp),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = when (data.transaction.paymentMethod) {
                        PaymentMethod.CASH -> "Bar"
                        PaymentMethod.CARD -> "Karte - " + data.transaction.externalTransactionId
                    }
                )
            }

            Text(text = formatPrice(data.transaction.amount), fontWeight = FontWeight.SemiBold)
        }

        if (detailsOpen.value) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.surfaceDim)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                data.lineItems.forEach {
                    Row {
                        Text(text = it.amount.toString() + "x")
                        Spacer(Modifier.width(4.dp))

                        Column {
                            Text(it.name)
                            if (it.depositPerUnit != null && it.depositPerUnit > 0) {
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = "+ " + formatPrice(it.depositPerUnit) + " Pfand",
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(Modifier.weight(1f))
                        Text(formatPrice(it.pricePerUnit * it.amount))
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemCashPreview() {
    TransactionItem(
        data = TransactionWithTransactionLineItems(
            transaction = Transaction(
                amount = 12.5,
                paymentMethod = PaymentMethod.CASH,
                transactionId = 12,
            ), lineItems = listOf(
                TransactionLineItem(
                    name = "Wurstbrot",
                    pricePerUnit = 1.5,
                    amount = 3,
                    transactionId = 12,
                    depositPerUnit = 0.0,
                    productId = 123,
                    transactionLineItemId = 1
                ),

                TransactionLineItem(
                    name = "Käsebrot",
                    pricePerUnit = 2.0,
                    amount = 1,
                    transactionId = 12,
                    depositPerUnit = 1.0,
                    productId = 456,
                    transactionLineItemId = 2
                ),
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TransactionItemCardPreview() {
    TransactionItem(
        data = TransactionWithTransactionLineItems(
            transaction = Transaction(
                amount = 4.0,
                paymentMethod = PaymentMethod.CARD,
                transactionId = 10,
                externalTransactionId = "ABC123"
            ), lineItems = listOf()
        )
    )
}