package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment

import androidx.activity.result.ActivityResultLauncher
import com.sumup.merchant.reader.api.SumUpAPI
import com.sumup.merchant.reader.api.SumUpPayment
import java.math.BigDecimal
import java.util.UUID

class Checkout {
    operator fun invoke(
        amount: Double,
        title: String,
        launcher: ActivityResultLauncher<SumUpPayment>
    ) {
        if (!SumUpAPI.isLoggedIn()) {
            return
        }

        val payment = SumUpPayment.builder()
            .total(BigDecimal(amount))
            .currency(SumUpPayment.Currency.EUR)
            .title(title)
            .foreignTransactionId(
                UUID.randomUUID().toString()
            )
            .skipSuccessScreen()
            .build()

        launcher.launch(payment)
    }
}