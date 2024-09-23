package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.CartUseCases
import io.bahuma.kassenkumpel.feature_products.domain.use_case.ProductUseCases
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.TransactionUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PointOfSaleViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
    private val productUseCases: ProductUseCases,
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {
    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    private val _lineItems = mutableStateListOf<LineItem>()
    val lineItems: List<LineItem> = _lineItems

    private var getProductsJob: Job? = null
    private var getLineItemsJob: Job? = null

    init {
        getProducts()
        getLineItems()
    }

    fun onEvent(event: PointOfSaleEvent) {
        when (event) {
            is PointOfSaleEvent.AddProductToCart -> {
                cartUseCases.addProductToCart(event.product, event.amount)
            }

            is PointOfSaleEvent.RemoveProductFromCart -> {
                cartUseCases.removeProductFromCart(event.productId, event.amount)
            }

            is PointOfSaleEvent.RemoveProductCompletelyFromCart -> {
                cartUseCases.removeProductCompletelyFromCart(event.productId)
            }

            is PointOfSaleEvent.ClearCartEvent -> {
                cartUseCases.clearCart()
            }

            is PointOfSaleEvent.PayEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val transactionLineItems = lineItems.map {
                        TransactionLineItem(
                            null,
                            it.name,
                            it.pricePerUnit,
                            it.amount,
                            it.productId
                        )
                    }

                    transactionUseCases.addTransaction(
                        Transaction(
                            cartUseCases.getCartTotal().first(),
                            event.paymentMethod
                        ), transactionLineItems
                    )

                    cartUseCases.clearCart()
                }
            }
        }
    }

    private fun getProducts() {
        getProductsJob?.cancel()

        getProductsJob = productUseCases.getProducts()
            .onEach { products ->
                _products.clear()
                _products.addAll(products)
            }
            .launchIn(viewModelScope)
    }

    private fun getLineItems() {
        getLineItemsJob?.cancel()

        getLineItemsJob = cartUseCases.getLineItemsInCart()
            .onEach { lineItems ->
                Log.i("PointOfSaleViewModel", "getLineItems: $lineItems")
                _lineItems.clear()
                _lineItems.addAll(lineItems)
            }
            .launchIn(viewModelScope)
    }
}