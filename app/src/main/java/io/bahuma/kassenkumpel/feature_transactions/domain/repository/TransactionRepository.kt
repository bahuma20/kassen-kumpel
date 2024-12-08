package io.bahuma.kassenkumpel.feature_transactions.domain.repository

import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>

    fun getTransactionsWithTransactionLineItems(): Flow<List<TransactionWithTransactionLineItems>>

    suspend fun insertTransaction(transaction: Transaction): Long

    suspend fun deleteAllTransactions(): Int
}