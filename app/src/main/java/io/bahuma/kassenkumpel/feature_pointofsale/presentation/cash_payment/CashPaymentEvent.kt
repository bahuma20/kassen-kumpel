package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment

sealed class CashPaymentEvent {
    data class PayedAmountChangedEvent(val newPayedAmount: Double) : CashPaymentEvent()
}