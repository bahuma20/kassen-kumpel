package io.bahuma.kassenkumpel.feature_products.domain.repository

import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<Category>>

    suspend fun getCategoryById(id: Int): Category?

    suspend fun insertCategory(category: Category)
}