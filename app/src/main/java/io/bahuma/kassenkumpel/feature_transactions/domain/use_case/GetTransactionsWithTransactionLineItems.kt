package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsWithTransactionLineItems(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<TransactionWithTransactionLineItems>> {
        return repository.getTransactionsWithTransactionLineItems()
    }
}