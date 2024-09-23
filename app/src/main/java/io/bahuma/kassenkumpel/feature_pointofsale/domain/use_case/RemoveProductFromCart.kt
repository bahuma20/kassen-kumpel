package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case

import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository

class RemoveProductFromCart(
    val repository: CartRepository
) {
    operator fun invoke(productId: Int, amount: Int = 1) {
        repository.removeProduct(productId, amount)
    }
}