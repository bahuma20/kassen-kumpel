package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionRepository

class DeleteAllTransactions(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(): Int {
        return repository.deleteAllTransactions()
    }
}