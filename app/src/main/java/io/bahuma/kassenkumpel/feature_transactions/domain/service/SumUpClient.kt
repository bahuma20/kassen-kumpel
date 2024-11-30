package io.bahuma.kassenkumpel.feature_transactions.domain.service

import retrofit2.http.GET
import retrofit2.http.Query

interface SumUpClient {
    @GET("me/transactions")
    suspend fun getTransaction(
        @Query("transaction_code") transactionCode: String
    ): Transaction
}