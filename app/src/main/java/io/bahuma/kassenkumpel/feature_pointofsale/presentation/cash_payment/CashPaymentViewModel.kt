package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = CashPaymentViewModel.CashPaymentViewModelFactory::class)
class CashPaymentViewModel @AssistedInject constructor(
    @Assisted private val totalAmount: Double,
    @Assisted private val countLineItems: Int,
) : ViewModel() {
    private val _state = mutableStateOf(CashPaymentState())
    val state: State<CashPaymentState> = _state

    @AssistedFactory
    interface CashPaymentViewModelFactory {
        fun create(totalAmount: Double, countLineItems: Int): CashPaymentViewModel
    }

    init {
        _state.value = state.value.copy(totalAmount = totalAmount, countLineItems = countLineItems)
    }

    fun onEvent(event: CashPaymentEvent) {
        when (event) {
            is CashPaymentEvent.PayedAmountChangedEvent -> {
                _state.value = _state.value.copy(
                    payedAmount = event.newPayedAmount
                )

                if (event.newPayedAmount > _state.value.totalAmount) {
                    _state.value = _state.value.copy(
                        changeAmount = event.newPayedAmount - _state.value.totalAmount
                    )
                } else {
                    _state.value = _state.value.copy(
                        changeAmount = 0.0
                    )
                }
            }
        }
    }
}