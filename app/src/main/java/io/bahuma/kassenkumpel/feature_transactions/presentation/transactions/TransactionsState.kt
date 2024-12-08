package io.bahuma.kassenkumpel.feature_transactions.presentation.transactions

import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems

data class TransactionsState(
    val transactions: List<TransactionWithTransactionLineItems> = emptyList()
)