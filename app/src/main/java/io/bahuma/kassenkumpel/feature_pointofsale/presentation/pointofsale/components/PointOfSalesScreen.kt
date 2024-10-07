package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.core.controller.SnackbarMessageHandler
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components.Cart
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components.CashPayment
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleEvent
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleViewModel
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.products.components.ProductGrid
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod

@Composable
fun PointOfSalesScreen(
    navController: NavController,
    viewModel: PointOfSaleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.value

    val itemsInCart: State<Map<Int, Int>> = remember {
        derivedStateOf {
            viewModel.lineItems
                .filter { it.productId != null }
                .associateBy({ it.productId ?: 0 }, { it.amount })
        }
    }

    SnackbarMessageHandler(
        snackbarMessage = uiState.snackbarMessage,
        { viewModel.onEvent(PointOfSaleEvent.SnackbarCloseEvent) })

    Row(modifier = modifier) {
        ProductGrid(
            viewModel.products,
            itemsInCart.value,
            onItemAdded = { item ->
                viewModel.onEvent(PointOfSaleEvent.AddProductToCart(item))
            },
            onItemRemoved = { item ->
                if (item.id != null) {
                    viewModel.onEvent(PointOfSaleEvent.RemoveProductFromCart(item.id))
                }
            },
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Color.White)
        )

        Cart(
            lineItems = viewModel.lineItems,
            onRemove = {
                Log.i("Bahumatest", "remove ${it.productId} x ${it.amount}")
                viewModel.onEvent(
                    PointOfSaleEvent.RemoveProductCompletelyFromCart(
                        it.productId ?: -1
                    )
                )
            },
            onPayCard = { viewModel.onEvent(PointOfSaleEvent.PayEvent(PaymentMethod.CARD)) },
            onPayCash = { viewModel.onEvent(PointOfSaleEvent.PayCashEvent) },
            onPayLater = {},
            onClearCart = { viewModel.onEvent(PointOfSaleEvent.ClearCartEvent) },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        )
    }

    if (uiState.cashPaymentModalOpen) {
        Dialog(onDismissRequest = {
            viewModel.onEvent(PointOfSaleEvent.ClosePaymentDialogEvent)
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors().copy(containerColor = Color.White)
            ) {
                CashPayment(
                    totalAmount = viewModel.cartTotal.value,
                    countLineItems = viewModel.lineItems.size,
                    onClose = { viewModel.onEvent(PointOfSaleEvent.ClosePaymentDialogEvent) },
                    onComplete = { viewModel.onEvent(PointOfSaleEvent.PayEvent(PaymentMethod.CASH)) },
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}