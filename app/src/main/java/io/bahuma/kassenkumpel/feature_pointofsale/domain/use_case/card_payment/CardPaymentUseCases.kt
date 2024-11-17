package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment

data class CardPaymentUseCases(
    val prepareCheckout: PrepareCheckout,
    val checkout: Checkout,
)