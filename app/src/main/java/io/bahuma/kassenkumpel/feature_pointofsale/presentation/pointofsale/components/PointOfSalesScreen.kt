package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.R
import io.bahuma.kassenkumpel.core.connectivity.ConnectionState
import io.bahuma.kassenkumpel.core.connectivity.connectivityState
import io.bahuma.kassenkumpel.core.controller.SnackbarMessageHandler
import io.bahuma.kassenkumpel.feature_pointofsale.domain.contract.SumUpPaymentForResult
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components.Cart
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components.CashPayment
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleEvent
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleEvent.PayCardResultEvent
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleViewModel
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.products.components.ProductGrid
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
@Composable
fun PointOfSalesScreen(
    navController: NavController,
    sumupLoginFunction: () -> Unit,
    viewModel: PointOfSaleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.value

    val context = LocalContext.current

    val sumupPaymentLauncher = rememberLauncherForActivityResult(SumUpPaymentForResult()) {
        viewModel.onEvent(PayCardResultEvent(it.data))
    }

    val itemsInCart: State<Map<Int, Int>> = remember {
        derivedStateOf {
            viewModel.lineItems
                .filter { it.productId != null }
                .associateBy({ it.productId ?: 0 }, { it.amount })
        }
    }

    val networkConnection = connectivityState()

    SnackbarMessageHandler(
        snackbarMessage = uiState.snackbarMessage,
        { viewModel.onEvent(PointOfSaleEvent.SnackbarCloseEvent) })

    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Color.White)
        ) {
            if (!uiState.isLoggedIn) {
                sumupLoginFunction()

                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    "Nicht eingeloggt!",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Um Zahlungen über SumUp zu akzeptieren, müsssen Sie Ihr SumUp-Konto verknüpfen.")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = sumupLoginFunction) {
                                    Text("Einloggen")
                                }
                            }
                        }
                    }
                }
            }

            if (networkConnection.value === ConnectionState.Unavailable) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column {
                                Text(
                                    "Keine Internetverbindung!",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Um Zahlungen über SumUp zu akzeptieren, muss das Gerät mit dem Internet verbunden sein. Bitte stellen Sie eine WLAN-Verbindung her. Barzahlung ist weiterhin möglich.")
                            }
                        }
                    }
                }
            }

            CategoryFilter(
                categories = viewModel.categories,
                selectedCategory = uiState.selectedCategory,
                onSelectedCategory = {
                    viewModel.onEvent(
                        PointOfSaleEvent.SelectCategory(it)
                    )
                }
            )

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
                }
            )
        }

        Cart(
            lineItems = viewModel.lineItems,
            totalAmount = viewModel.cartTotal.value,
            cardPaymentAvailable = uiState.isLoggedIn && networkConnection.value === ConnectionState.Available,
            onRemoveLineItem = {
                viewModel.onEvent(
                    PointOfSaleEvent.RemoveProductCompletelyFromCart(
                        it.productId ?: -1
                    )
                )
            },
            onChangeLineItemAmount = { lineItem, newAmount ->
                viewModel.onEvent(
                    PointOfSaleEvent.ChangeLineItemAmount(
                        lineItem.productId ?: -1,
                        newAmount
                    )
                )
            },
            onPayCard = {
                viewModel.onEvent(
                    PointOfSaleEvent.PayCardEvent(
                        context.getString(R.string.transaction_title),
                        sumupPaymentLauncher
                    )
                )
            },
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
            viewModel.onEvent(PointOfSaleEvent.CloseCashPaymentDialogEvent)
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
                    onClose = { viewModel.onEvent(PointOfSaleEvent.CloseCashPaymentDialogEvent) },
                    onComplete = { viewModel.onEvent(PointOfSaleEvent.PayEvent(PaymentMethod.CASH)) },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            Log.i("PointOfSalesScreen", "checkLoginState")
            viewModel.checkLoginState()
            delay(2000)
        }
    }
}