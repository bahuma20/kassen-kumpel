package io.bahuma.kassenkumpel.feature_products.domain.use_case.product

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(includeDeleted: Boolean = false): Flow<List<Product>> {
        var flow = repository.getProducts()

        if (!includeDeleted) {
            flow = flow.map { products -> products.filter { product -> !product.deleted }.toList() }
        }

        return flow.map { products ->
            products.sortedWith(
                compareBy(
                    { it.color },
                    { it.name.lowercase() })
            )
        }
    }
}