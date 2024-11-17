package io.bahuma.kassenkumpel.feature_pointofsale.domain.model

data class LineItem(
    val name: String,
    val pricePerUnit: Double,
    val amount: Int,
    val productId: Int?,
    val depositPerUnit: Double?,
)
