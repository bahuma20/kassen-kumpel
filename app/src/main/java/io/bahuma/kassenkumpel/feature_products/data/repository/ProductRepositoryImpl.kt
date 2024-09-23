package io.bahuma.kassenkumpel.feature_products.data.repository

import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.data.data_source.ProductDao
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(
    private val dao: ProductDao
) : ProductRepository {
    override fun getProducts(): Flow<List<Product>> {
        return dao.getProducts()
    }

    override suspend fun getProductById(id: Int): Product? {
        return dao.getProductById(id)
    }

    override suspend fun insertProduct(product: Product) {
        dao.insertProduct(product)
    }

}