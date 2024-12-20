package io.bahuma.kassenkumpel.feature_transactions.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionLineItem(
    val transactionId: Long?,
    val name: String,
    val pricePerUnit: Double,
    val amount: Int,
    @ColumnInfo(defaultValue = "0.0") val depositPerUnit: Double?,
    val productId: Int?,
    @PrimaryKey val transactionLineItemId: Long? = null
)