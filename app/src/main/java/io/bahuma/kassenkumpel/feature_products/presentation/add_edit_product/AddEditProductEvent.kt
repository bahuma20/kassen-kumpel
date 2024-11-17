package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product

sealed class AddEditProductEvent {
    data class EnteredName(val value: String) : AddEditProductEvent()
    data class EnteredPrice(val value: String) : AddEditProductEvent()
    data class EnteredDeposit(val value: String) : AddEditProductEvent()

    data class ChangeColor(val value: Int) : AddEditProductEvent()
    data class SelectedCategory(val value: Int?) : AddEditProductEvent()
    data object SaveProduct : AddEditProductEvent()
    data object OpenCategoryDropdown : AddEditProductEvent()
    data object CloseCategoryDropdown : AddEditProductEvent()
}
