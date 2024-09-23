package io.bahuma.kassenkumpel.feature_pointofsale.domain.repository

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun addProduct(product: Product, amount: Int = 1)
    fun removeProduct(productId: Int, amount: Int = 1)
    fun removeProductCompletely(productId: Int)
    fun getTotal(): Double
    fun getLineItems(): Flow<List<LineItem>>
    fun clearCart()
}