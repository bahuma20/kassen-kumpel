package io.bahuma.kassenkumpel.feature_products.presentation.categories

import io.bahuma.kassenkumpel.feature_products.domain.model.Category

data class CategoriesState(
    val categories: List<Category> = emptyList(),
    val categoryToDelete: Category? = null
)