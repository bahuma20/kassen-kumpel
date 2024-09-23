package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components.Cart
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleEvent
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.PointOfSaleViewModel
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.products.components.ProductGrid
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PointOfSalesScreen(
    navController: NavController,
    viewModel: PointOfSaleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val itemsInCart: State<Map<Int, Int>> = remember {
        derivedStateOf {
            viewModel.lineItems
                .filter { it.productId != null }
                .associateBy({ it.productId ?: 0 }, { it.amount })
        }
    }

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
            onPayCash = { viewModel.onEvent(PointOfSaleEvent.PayEvent(PaymentMethod.CASH)) },
            onPayLater = {},
            onClearCart = { viewModel.onEvent(PointOfSaleEvent.ClearCartEvent) },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        )
    }
}