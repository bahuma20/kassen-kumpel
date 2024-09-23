package io.bahuma.kassenkumpel.feature_products.domain.repository

import io.bahuma.kassenkumpel.core.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<List<Product>>

    suspend fun getProductById(id: Int): Product?

    suspend fun insertProduct(product: Product)
}