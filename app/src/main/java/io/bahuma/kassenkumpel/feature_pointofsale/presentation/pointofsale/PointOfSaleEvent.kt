package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.sumup.merchant.reader.api.SumUpPayment
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_transactions.domain.model.PaymentMethod

sealed class PointOfSaleEvent {
    class AddProductToCart(val product: Product, val amount: Int = 1) : PointOfSaleEvent()
    class RemoveProductFromCart(val productId: Int, val amount: Int = 1) : PointOfSaleEvent()
    class RemoveProductCompletelyFromCart(val productId: Int) : PointOfSaleEvent()
    data object ClearCartEvent : PointOfSaleEvent()
    class PayEvent(val paymentMethod: PaymentMethod, val externalTransactionId: String? = null) :
        PointOfSaleEvent()

    class ChangeLineItemAmount(val productId: Int, val newAmount: Int) : PointOfSaleEvent()
    data object PayCashEvent : PointOfSaleEvent()
    class PayCardEvent(val title: String, val launcher: ActivityResultLauncher<SumUpPayment>) :
        PointOfSaleEvent()

    class PayCardResultEvent(val intent: Intent?) : PointOfSaleEvent()
    data object CloseCashPaymentDialogEvent : PointOfSaleEvent()
    data object SnackbarCloseEvent : PointOfSaleEvent()
    class SelectCategory(val category: Category?) : PointOfSaleEvent()
}