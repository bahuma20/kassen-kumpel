package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category

import io.bahuma.kassenkumpel.feature_products.domain.model.CategoryIcon

sealed class AddEditCategoryEvent {
    data class EnteredName(val value: String) : AddEditCategoryEvent()
    data class ChangeIcon(val value: CategoryIcon?) : AddEditCategoryEvent()
    data object SaveCategory : AddEditCategoryEvent()
}
