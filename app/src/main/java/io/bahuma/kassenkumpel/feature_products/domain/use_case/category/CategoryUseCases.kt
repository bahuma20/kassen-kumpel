package io.bahuma.kassenkumpel.feature_products.domain.use_case.category

data class CategoryUseCases(
    val getCategories: GetCategories,
    val getCategory: GetCategory,
    val addCategory: AddCategory,
    val deleteCategory: DeleteCategory
)