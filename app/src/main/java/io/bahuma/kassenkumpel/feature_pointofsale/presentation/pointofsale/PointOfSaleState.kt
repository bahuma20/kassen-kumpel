package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import io.bahuma.kassenkumpel.core.controller.SnackbarMessage
import io.bahuma.kassenkumpel.feature_products.domain.model.Category

data class PointOfSaleState(
    val selectedCategory: Category? = null,
    val cashPaymentModalOpen: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null,
    val isLoggedIn: Boolean = false,
)
