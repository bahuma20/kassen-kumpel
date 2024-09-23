package io.bahuma.kassenkumpel.feature_transactions.domain.repository

import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>

    suspend fun insertTransaction(transaction: Transaction): Long
}