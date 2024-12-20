package io.bahuma.kassenkumpel.feature_transactions.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionWithTransactionLineItems
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun getTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY timestamp DESC")
    @androidx.room.Transaction
    fun getTransactionsWithTransactionLineItems(): Flow<List<TransactionWithTransactionLineItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction): Long

    @Query("DELETE FROM `transaction`")
    fun deleteAllTransactions(): Int
}