package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart

import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetLineItemsInCart(
    val repository: CartRepository
) {
    operator fun invoke(): Flow<List<LineItem>> {
        return repository.getLineItems()
    }
}