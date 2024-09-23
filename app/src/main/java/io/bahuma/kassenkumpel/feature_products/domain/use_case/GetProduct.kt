package io.bahuma.kassenkumpel.feature_products.domain.use_case

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository

class GetProduct(
    private val repository: ProductRepository
) {

    suspend operator fun invoke(id: Int): Product? {
        return repository.getProductById(id)
    }
}