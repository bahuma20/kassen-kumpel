package io.bahuma.kassenkumpel.feature_transactions.presentation.transactions

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.TransactionUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {
    private val _state = mutableStateOf(TransactionsState())
    val state: State<TransactionsState> = _state

    private var getTransactionsJob: Job? = null

    init {
        getTransactions()
    }

    private fun getTransactions() {
        getTransactionsJob?.cancel()

        getTransactionsJob = transactionUseCases.getTransactions().onEach {
            _state.value = state.value.copy(
                transactions = it
            )
        }.launchIn(viewModelScope)
    }
}