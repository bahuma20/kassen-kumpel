package io.bahuma.kassenkumpel.feature_products.data.repository

import io.bahuma.kassenkumpel.feature_products.data.data_source.CategoryDao
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val dao: CategoryDao
) : CategoryRepository {
    override fun getCategories(): Flow<List<Category>> {
        return dao.getCategories()
    }

    override suspend fun getCategoryById(id: Int): Category? {
        return dao.getCategoryById(id)
    }

    override suspend fun insertCategory(category: Category) {
        return dao.insertCategory(category)
    }
}