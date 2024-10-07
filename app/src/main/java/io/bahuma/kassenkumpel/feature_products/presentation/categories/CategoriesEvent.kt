package io.bahuma.kassenkumpel.feature_products.presentation.categories

import io.bahuma.kassenkumpel.feature_products.domain.model.Category

sealed class CategoriesEvent {
    data class DeleteCategory(val category: Category) : CategoriesEvent()
    data class DeleteCategoryConfirm(val category: Category) : CategoriesEvent()
    data object DeleteCategoryCancel : CategoriesEvent()
}