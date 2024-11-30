package io.bahuma.kassenkumpel.feature_transactions.domain.use_case

import io.bahuma.kassenkumpel.feature_transactions.domain.service.SumUpClient
import io.bahuma.kassenkumpel.feature_transactions.domain.service.Transaction

class GetExternalTransaction(
    private val sumUpClient: SumUpClient
) {

    suspend operator fun invoke(transactionCode: String): Transaction {
        // TODO: Implement this
        return sumUpClient.getTransaction(
            transactionCode
        )
    }
}