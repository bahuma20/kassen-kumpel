package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart

import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCartTotal(
    val repository: CartRepository
) {
    operator fun invoke(): Flow<Double> {
        return repository.getLineItems().map { lineItems ->
            lineItems.map { it.amount * (it.pricePerUnit + (it.depositPerUnit ?: 0.0)) }
                .fold(0.0) { acc, value -> acc + value }
        }
    }
}