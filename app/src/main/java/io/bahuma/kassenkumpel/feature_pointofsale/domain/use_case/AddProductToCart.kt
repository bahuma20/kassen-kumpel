package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository

class AddProductToCart(
    val repository: CartRepository
) {
    operator fun invoke(product: Product, amount: Int = 1) {
        repository.addProduct(product, amount)
    }
}