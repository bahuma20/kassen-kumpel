package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case

import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository

class RemoveProductCompletelyFromCart(
    val repository: CartRepository
) {
    operator fun invoke(productId: Int) {
        repository.removeProductCompletely(productId)
    }
}