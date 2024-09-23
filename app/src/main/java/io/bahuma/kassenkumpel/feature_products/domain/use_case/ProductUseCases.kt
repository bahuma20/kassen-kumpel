package io.bahuma.kassenkumpel.feature_products.domain.use_case

data class ProductUseCases(
    val getProducts: GetProducts,
    val getProduct: GetProduct,
    val deleteProduct: DeleteProduct,
    val addProduct: AddProduct
)
