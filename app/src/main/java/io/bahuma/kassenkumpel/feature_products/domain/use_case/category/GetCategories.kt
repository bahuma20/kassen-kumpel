package io.bahuma.kassenkumpel.feature_products.domain.use_case.category

import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategories(
    private val repository: CategoryRepository
) {
    operator fun invoke(includeDeleted: Boolean = false): Flow<List<Category>> {
        var flow = repository.getCategories()

        if (!includeDeleted) {
            flow = flow.map { categories ->
                categories.filter { category -> !category.deleted }.toList()
            }
        }

        return flow.map { categories ->
            categories.sortedWith(
                compareBy(
                    { it.weight },
                    { it.name.lowercase() })
            )
        }
    }
}