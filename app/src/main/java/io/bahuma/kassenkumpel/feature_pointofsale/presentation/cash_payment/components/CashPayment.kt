package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.CashPaymentState
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components.numberinput.NumberInput
import io.bahuma.kassenkumpel.utils.formatPrice
import kotlinx.coroutines.launch

@Composable
fun CashPayment(
    totalAmount: Double,
    countLineItems: Int,
    onClose: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(CashPaymentState()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(totalAmount, countLineItems) {
        coroutineScope.launch {
            state = state.copy(
                totalAmount = totalAmount,
                countLineItems = countLineItems,
                changeAmount = 0.0
            )
        }
    }

    LaunchedEffect(state.payedAmount, state.totalAmount) {
        coroutineScope.launch {
            if (state.payedAmount > state.totalAmount) {
                state = state.copy(changeAmount = state.payedAmount - state.totalAmount)
            } else {
                state = state.copy(changeAmount = 0.0)
            }
        }
    }

    CashPayment(
        totalAmount = state.totalAmount,
        countLineItems = state.countLineItems,
        payedAmount = state.payedAmount,
        changeAmount = state.changeAmount,
        onClose = onClose,
        onPayedAmountChanged = { newPayedAmount ->
            state = state.copy(payedAmount = newPayedAmount)
        },
        onComplete = onComplete,
        modifier = modifier
    )
}


@Composable
fun CashPayment(
    totalAmount: Double,
    countLineItems: Int,
    payedAmount: Double,
    changeAmount: Double,
    modifier: Modifier = Modifier,
    onComplete: () -> Unit = {},
    onClose: () -> Unit = {},
    onPayedAmountChanged: (Double) -> Unit = {},
) {
    val changeAmountActive: Boolean = remember(changeAmount) { changeAmount > 0 }
    val completeButtonActive: Boolean = remember(payedAmount) { payedAmount >= totalAmount }

    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Barzahlung",
                style = MaterialTheme.typography.headlineLarge,
            )

            Text(
                text = formatPrice(totalAmount),
                style = MaterialTheme.typography.displayMedium
            )

            Text(
                text = "$countLineItems Artikel",
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gezahlter Betrag")
                Text(
                    text = formatPrice(payedAmount),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Box(
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Fälliges Wechselgeld",
                    color = if (changeAmountActive) {
                        androidx.compose.material3.LocalContentColor.current
                    } else {
                        Color.Gray
                    },
                )
                Text(
                    text = formatPrice(changeAmount),
                    color = if (changeAmountActive) {
                        androidx.compose.material3.LocalContentColor.current
                    } else {
                        Color.Gray
                    },
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.weight(1f))

            NumberInput(onValue = onPayedAmountChanged)

            Spacer(Modifier.weight(1f))

            Row {
                OutlinedButton(
                    onClick = onComplete,
                    contentPadding = PaddingValues(32.dp, 16.dp),
                ) {
                    Text("Passend")
                }

                Spacer(Modifier.width(16.dp))

                Button(
                    onClick = onComplete,
                    enabled = completeButtonActive,
                    contentPadding = PaddingValues(32.dp, 16.dp),
                ) {
                    Text("Bezahlen")
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

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun CashPaymentScreenPreview() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CashPayment(22.96, 6, 12.0, 0.0)
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun CashPaymentScreenPreview2() {
    CashPayment(22.96, 6, 30.0, 7.04)
}