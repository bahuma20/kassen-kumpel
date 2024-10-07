package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod

sealed class PointOfSaleEvent {
    class AddProductToCart(val product: Product, val amount: Int = 1) : PointOfSaleEvent()
    class RemoveProductFromCart(val productId: Int, val amount: Int = 1) : PointOfSaleEvent()
    class RemoveProductCompletelyFromCart(val productId: Int) : PointOfSaleEvent()
    data object ClearCartEvent : PointOfSaleEvent()
    class PayEvent(val paymentMethod: PaymentMethod) : PointOfSaleEvent()
    data object PayCashEvent : PointOfSaleEvent()
    data object ClosePaymentDialogEvent : PointOfSaleEvent()
    data object SnackbarCloseEvent : PointOfSaleEvent()
}