package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.core.controller.SnackbarMessage
import io.bahuma.kassenkumpel.core.controller.UserMessage
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.CartUseCases
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.CategoryUseCases
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.ProductUseCases
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
    private val categoryUseCases: CategoryUseCases,
    private val productUseCases: ProductUseCases,
    private val cartUseCases: CartUseCases,
    private val transactionUseCases: TransactionUseCases,
) : ViewModel() {
    private val _categories = mutableStateListOf<Category>()
    val categories: List<Category> = _categories

    private val _products = mutableStateListOf<Product>()
    val products: List<Product> = _products

    private val _lineItems = mutableStateListOf<LineItem>()
    val lineItems: List<LineItem> = _lineItems

    private val _cartTotal = mutableDoubleStateOf(0.0)
    val cartTotal: State<Double> = _cartTotal

    private val _uiState = mutableStateOf(PointOfSaleState())
    val uiState: State<PointOfSaleState> = _uiState


    private var getCategoriesJob: Job? = null
    private var getProductsJob: Job? = null
    private var getLineItemsJob: Job? = null
    private var getCartTotalJob: Job? = null

    init {
        getCategories()
        getProducts()
        getLineItems()
        getCartTotal()
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

            is PointOfSaleEvent.ChangeLineItemAmount -> {
                cartUseCases.setProductAmount(event.productId, event.newAmount)
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

                    _uiState.value = uiState.value.copy(
                        cashPaymentModalOpen = false,
                        snackbarMessage = SnackbarMessage.from(
                            userMessage = UserMessage.from("Zahlung erfolgreich"),
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    )
                }
            }

            PointOfSaleEvent.PayCashEvent -> {
                _uiState.value = uiState.value.copy(cashPaymentModalOpen = true)
            }

            PointOfSaleEvent.ClosePaymentDialogEvent -> {
                _uiState.value = uiState.value.copy(cashPaymentModalOpen = false)
            }

            PointOfSaleEvent.SnackbarCloseEvent -> {
                _uiState.value = uiState.value.copy(snackbarMessage = null)
            }

            is PointOfSaleEvent.SelectCategory -> {
                _uiState.value = uiState.value.copy(selectedCategory = event.category)
                getProducts(event.category?.categoryId)
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()

        getCategoriesJob = categoryUseCases.getCategories()
            .onEach { categories ->
                _categories.clear()
                _categories.addAll(categories)
            }
            .launchIn(viewModelScope)
    }

    private fun getProducts(filterByCategoryId: Int? = null) {
        getProductsJob?.cancel()

        getProductsJob = productUseCases.getProducts(false, filterByCategoryId)
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

    private fun getCartTotal() {
        getCartTotalJob?.cancel()

        getCartTotalJob = cartUseCases.getCartTotal()
            .onEach { total -> _cartTotal.doubleValue = total }
            .launchIn(viewModelScope)
    }

}