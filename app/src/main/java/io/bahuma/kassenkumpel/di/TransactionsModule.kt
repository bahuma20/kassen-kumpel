package io.bahuma.kassenkumpel.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bahuma.kassenkumpel.feature_transactions.data.data_source.TransactionDatabase
import io.bahuma.kassenkumpel.feature_transactions.data.repository.TransactionLineItemRepositoryImpl
import io.bahuma.kassenkumpel.feature_transactions.data.repository.TransactionRepositoryImpl
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionLineItemRepository
import io.bahuma.kassenkumpel.feature_transactions.domain.repository.TransactionRepository
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.AddTransaction
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.GetTransactions
import io.bahuma.kassenkumpel.feature_transactions.domain.use_case.TransactionUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionsModule {
    @Provides
    @Singleton
    fun provideTransactionsDatabase(app: Application): TransactionDatabase {
        return Room.databaseBuilder(
            app,
            TransactionDatabase::class.java,
            TransactionDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionsRepository(db: TransactionDatabase): TransactionRepository {
        return TransactionRepositoryImpl(db.transactionDao)
    }

    @Provides
    @Singleton
    fun provideTransactionLineItemRepository(db: TransactionDatabase): TransactionLineItemRepository {
        return TransactionLineItemRepositoryImpl(db.transactionLineItemDao)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCases(
        transactionRepository: TransactionRepository,
        transactionLineItemRepository: TransactionLineItemRepository,
    ): TransactionUseCases {
        return TransactionUseCases(
            getTransactions = GetTransactions(transactionRepository),
            addTransaction = AddTransaction(transactionRepository, transactionLineItemRepository)
        )
    }

}