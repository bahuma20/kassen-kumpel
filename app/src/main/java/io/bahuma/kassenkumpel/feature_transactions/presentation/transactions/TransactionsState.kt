package io.bahuma.kassenkumpel.feature_transactions.presentation.transactions

import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction

data class TransactionsState(
    val transactions: List<Transaction> = emptyList()
)