package io.bahuma.kassenkumpel.feature_transactions.domain.repository

import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem

interface TransactionLineItemRepository {
    suspend fun insertAll(transactionLineItems: Collection<TransactionLineItem>)
}