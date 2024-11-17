package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

import io.bahuma.kassenkumpel.feature_transactions.domain.model.InvalidTransactionException
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionLineItemRepository
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionRepository

class AddTransaction(
    private val transactionRepository: TransactionRepository,
    private val transactionLineItemRepository: TransactionLineItemRepository
) {
    suspend operator fun invoke(
        transaction: Transaction,
        transactionLineItems: List<TransactionLineItem>
    ) {
        if (transactionLineItems.isEmpty()) {
            throw InvalidTransactionException("Transaction must have at least one line item")
        }

        val transactionId = transactionRepository.insertTransaction(transaction)

        val lineItemsWithTransactionId = transactionLineItems.map { lineItem ->
            lineItem.copy(transactionId = transactionId)
        }

        transactionLineItemRepository.insertAll(lineItemsWithTransactionId)
    }
}