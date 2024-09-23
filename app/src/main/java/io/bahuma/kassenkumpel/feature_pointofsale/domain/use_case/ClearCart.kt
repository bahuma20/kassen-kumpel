package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case

import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository

class ClearCart(
    val repository: CartRepository
) {
    operator fun invoke() {
        repository.clearCart()
    }

}