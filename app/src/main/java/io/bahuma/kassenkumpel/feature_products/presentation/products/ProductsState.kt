package io.bahuma.kassenkumpel.feature_products.presentation.products

import io.bahuma.kassenkumpel.core.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val productToDelete: Product? = null,
)
