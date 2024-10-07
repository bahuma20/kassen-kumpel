package io.bahuma.kassenkumpel.feature_products.domain.use_case.category

import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository

class DeleteCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        val deletedCategory = category.copy(deleted = true)
        repository.insertCategory(deletedCategory)
    }
}