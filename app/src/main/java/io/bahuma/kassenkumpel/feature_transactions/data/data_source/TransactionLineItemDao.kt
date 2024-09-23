package io.bahuma.kassenkumpel.feature_transactions.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem

@Dao
interface TransactionLineItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactionLineItems: Collection<TransactionLineItem>)
}