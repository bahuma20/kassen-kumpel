package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment

import androidx.activity.result.ActivityResultLauncher
import com.sumup.merchant.reader.api.SumUpAPI
import com.sumup.merchant.reader.api.SumUpPayment
import java.math.BigDecimal
import java.util.UUID

class Checkout {
    operator fun invoke(amount: Double, launcher: ActivityResultLauncher<SumUpPayment>) {
        if (!SumUpAPI.isLoggedIn()) {
            return
        }

        val payment = SumUpPayment.builder()
            .total(BigDecimal(amount))
            .currency(SumUpPayment.Currency.EUR)
            .title("Ihr Einkauf bei KassenKumpel") // TODO: make configurable
            .foreignTransactionId(
                UUID.randomUUID().toString()
            ) // TODO: use correct transaction id and pass back to transaction (or is TX_CODE enough and we don't need this?)
            .skipSuccessScreen()
            .build()

        launcher.launch(payment)
    }
}