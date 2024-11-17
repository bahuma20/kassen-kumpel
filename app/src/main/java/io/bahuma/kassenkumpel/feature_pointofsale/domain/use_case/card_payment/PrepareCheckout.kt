package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment

import com.sumup.merchant.reader.api.SumUpAPI

class PrepareCheckout {
    /**
     * Warms up the SumUp terminal (connecting to it, waking up from sleep, preparing for payment).
     * This call is not strictly necessary, but speeds up the payment process.
     */
    operator fun invoke() {
        if (!SumUpAPI.isLoggedIn()) {
            return
        }

        SumUpAPI.prepareForCheckout()
    }
}