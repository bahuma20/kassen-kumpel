package io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case

data class CartUseCases(
    val addProductToCart: AddProductToCart,
    val removeProductFromCart: RemoveProductFromCart,
    val removeProductCompletelyFromCart: RemoveProductCompletelyFromCart,
    val getLineItemsInCart: GetLineItemsInCart,
    val getCartTotal: GetCartTotal,
    val clearCart: ClearCart
)