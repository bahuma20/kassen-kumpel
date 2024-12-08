package io.bahuma.kassenkumpel.feature_transactions.data.repository

import io.bahuma.kassenkumpel.feature_transactions.data.data_source.TransactionDao
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactions()
    }

    override fun getTransactionsWithTransactionLineItems(): Flow<List<TransactionWithTransactionLineItems>> {
        return transactionDao.getTransactionsWithTransactionLineItems()
    }

    override suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction)
    }
}