package io.bahuma.kassenkumpel.feature_transactions.data.repository

import io.bahuma.kassenkumpel.feature_transactions.data.data_source.TransactionLineItemDao
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionLineItemRepository

class TransactionLineItemRepositoryImpl(
    private val dao: TransactionLineItemDao
) : TransactionLineItemRepository {
    override suspend fun insertAll(transactionLineItems: Collection<TransactionLineItem>) {
        dao.insertAll(transactionLineItems)
    }
}