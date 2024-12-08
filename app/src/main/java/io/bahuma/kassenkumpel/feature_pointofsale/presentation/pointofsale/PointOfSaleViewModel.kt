package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumup.merchant.reader.api.SumUpAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.core.controller.SnackbarMessage
import io.bahuma.kassenkumpel.core.controller.UserMessage
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment.CardPaymentUseCases
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.CartUseCases
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.CategoryUseCases
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.ProductUseCases
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod
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
    private val cardPaymentUseCases: CardPaymentUseCases,
) : ViewModel() {
    private val TAG = "PointOfSaleViewModel"

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
        checkLoginState()
    }

    fun onEvent(event: PointOfSaleEvent) {
        when (event) {
            is PointOfSaleEvent.AddProductToCart -> {
                if (lineItems.isEmpty()) {
                    // Prepare card payment when first item is added to cart
                    cardPaymentUseCases.prepareCheckout()
                }

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
                if (event.paymentMethod == PaymentMethod.CARD && event.externalTransactionId == null) {
                    throw IllegalStateException("A external transaction id is required for a card payment")
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val transactionLineItems = lineItems.map {
                        TransactionLineItem(
                            null,
                            it.name,
                            it.pricePerUnit,
                            it.amount,
                            it.depositPerUnit,
                            it.productId
                        )
                    }

                    transactionUseCases.addTransaction(
                        Transaction(
                            amount = cartUseCases.getCartTotal().first(),
                            paymentMethod = event.paymentMethod,
                            externalTransactionId = event.externalTransactionId
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

            is PointOfSaleEvent.PayCardEvent -> {
                cardPaymentUseCases.checkout(cartTotal.value, R.string, event.launcher)
            }

            is PointOfSaleEvent.PayCardResultEvent -> {
                val extra = event.intent?.extras

                val resultCode = extra?.getInt(SumUpAPI.Response.RESULT_CODE)
                val message = extra?.getString(SumUpAPI.Response.MESSAGE)

                val txCode = extra?.getString(SumUpAPI.Response.TX_CODE)

                val transactionInfo = extra?.getString(SumUpAPI.Response.TX_INFO)

                Log.i(TAG, "launcher: $resultCode $message $txCode $transactionInfo")

                if (resultCode == SumUpAPI.Response.ResultCode.SUCCESSFUL) {
                    onEvent(PointOfSaleEvent.PayEvent(PaymentMethod.CARD, txCode))
                } else {
                    // TODO: Error handling. Is anything needed? The SumUp SDK already displays the error page...
                }
            }

            PointOfSaleEvent.CloseCashPaymentDialogEvent -> {
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
                Log.i(TAG, "getLineItems: $lineItems")
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

    fun checkLoginState() {
        val loginState = SumUpAPI.isLoggedIn()
        if (loginState != uiState.value.isLoggedIn) {
            _uiState.value = uiState.value.copy(isLoggedIn = loginState)
        }
    }

}