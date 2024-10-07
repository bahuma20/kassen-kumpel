package io.bahuma.kassenkumpel.feature_products.domain.use_case.category

import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository

class GetCategory(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Int): Category? {
        return repository.getCategoryById(id)
    }

}