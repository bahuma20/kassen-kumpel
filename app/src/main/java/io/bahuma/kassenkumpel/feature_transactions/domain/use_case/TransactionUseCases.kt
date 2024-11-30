package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

data class TransactionUseCases(
    val addTransaction: AddTransaction,
    val getTransactions: GetTransactions,
    val getExternalTransaction: GetExternalTransaction,
)