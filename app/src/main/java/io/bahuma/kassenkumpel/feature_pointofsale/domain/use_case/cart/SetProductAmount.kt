package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart

import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository

class SetProductAmount(
    val repository: CartRepository
) {
    operator fun invoke(productId: Int, amount: Int) {
        repository.setProductAmount(productId, amount)
    }
}