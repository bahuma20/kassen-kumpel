package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale

import io.bahuma.kassenkumpel.core.controller.SnackbarMessage

data class PointOfSaleState(
    val cashPaymentModalOpen: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null,
)
