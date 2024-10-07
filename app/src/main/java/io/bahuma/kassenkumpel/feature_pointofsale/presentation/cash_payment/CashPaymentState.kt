package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment

data class CashPaymentState(
    val totalAmount: Double = 0.0,
    val countLineItems: Int = 0,
    val payedAmount: Double = 0.0,
    val changeAmount: Double = 0.0
)
