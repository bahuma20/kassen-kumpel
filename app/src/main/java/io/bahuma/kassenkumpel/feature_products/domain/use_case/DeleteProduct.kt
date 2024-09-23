package io.bahuma.kassenkumpel.feature_products.domain.use_case

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository

class DeleteProduct(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        val deletedProduct = product.copy(deleted = true)
        repository.insertProduct(deletedProduct)
    }
}