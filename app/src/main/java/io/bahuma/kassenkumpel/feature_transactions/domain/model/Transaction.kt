package io.bahuma.kassenkumpel.feature_transactions.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import io.bahuma.kassenkumpel.feature_transactions.domain.util.type_converters.InstantConverter
import java.time.Instant

@Entity
@TypeConverters(InstantConverter::class)
data class Transaction(
    val amount: Double,
    val paymentMethod: PaymentMethod,
    val timestamp: Instant = Instant.now(),
    @PrimaryKey val transactionId: Long? = null,
)

enum class PaymentMethod {
    CASH,
    CARD,
}

data class TransactionWithTransactionLineItems(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "transactionId",
        entityColumn = "transactionId"
    )
    val lineItems: List<TransactionLineItem>
)

class InvalidTransactionException(message: String) : Exception(message)