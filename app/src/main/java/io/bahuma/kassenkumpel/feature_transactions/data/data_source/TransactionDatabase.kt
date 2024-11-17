package io.bahuma.kassenkumpel.feature_transactions.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem

@Database(
    entities = [Transaction::class, TransactionLineItem::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ]
)
abstract class TransactionDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val transactionLineItemDao: TransactionLineItemDao

    companion object {
        const val DATABASE_NAME = "transactions_db"
    }
}