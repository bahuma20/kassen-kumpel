package io.bahuma.kassenkumpel.feature_products.presentation.products

import io.bahuma.kassenkumpel.core.model.Product

sealed class ProductsEvent {
    data class DeleteProduct(val product: Product) : ProductsEvent()
    data class DeleteProductConfirm(val product: Product) : ProductsEvent()
    data object DeleteProductCancel : ProductsEvent()
}