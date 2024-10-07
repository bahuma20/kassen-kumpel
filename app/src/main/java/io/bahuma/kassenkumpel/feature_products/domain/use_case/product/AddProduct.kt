package io.bahuma.kassenkumpel.feature_products.domain.use_case.product

import io.bahuma.kassenkumpel.core.model.InvalidProductException
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.core.model.ProductColor
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository

class AddProduct(
    private val repository: ProductRepository
) {

    @Throws(InvalidProductException::class)
    suspend operator fun invoke(product: Product) {
        if (product.name.isBlank()) {
            throw InvalidProductException("The title of the product can't be empty.")
        }

        if (product.price.isNaN() || product.price <= 0) {
            throw InvalidProductException("The product price is invalid.")
        }

        if (product.color > ProductColor.entries.size - 1 || product.color < 0) {
            throw InvalidProductException("The product color is invalid.")
        }

        repository.insertProduct(product)
    }
}