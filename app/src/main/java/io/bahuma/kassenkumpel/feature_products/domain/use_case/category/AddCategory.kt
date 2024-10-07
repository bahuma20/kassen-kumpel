package io.bahuma.kassenkumpel.feature_products.domain.use_case.category

import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.model.InvalidCategoryException
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository

class AddCategory(
    private val repository: CategoryRepository
) {
    @Throws(InvalidCategoryException::class)
    suspend operator fun invoke(category: Category) {
        if (category.name.isBlank()) {
            throw InvalidCategoryException("The title of the category can't be empty.")
        }

        repository.insertCategory(category)
    }
}