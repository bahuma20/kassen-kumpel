package io.bahuma.kassenkumpel.feature_transactions.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import io.bahuma.kassenkumpel.feature_transactions.domain.model.Transaction
import io.bahuma.kassenkumpel.feature_transactions.domain.model.TransactionLineItem

@Database(
    entities = [Transaction::class, TransactionLineItem::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
    ]
)
abstract class TransactionDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val transactionLineItemDao: TransactionLineItemDao

    companion object {
        const val DATABASE_NAME = "transactions_db"
    }
}