package io.bahuma.kassenkumpel.feature_products.domain.use_case.product

data class ProductUseCases(
    val getProducts: GetProducts,
    val getProduct: GetProduct,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct
)
