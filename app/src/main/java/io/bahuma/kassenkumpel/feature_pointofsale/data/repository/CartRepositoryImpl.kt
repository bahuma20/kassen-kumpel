package io.bahuma.kassenkumpel.feature_pointofsale.data.repository

import androidx.compose.runtime.mutableStateListOf
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl : CartRepository {
    private val _lineItems: MutableList<LineItem> = mutableStateListOf()
    private val _lineItemsFlow: MutableStateFlow<List<LineItem>> =
        MutableStateFlow(_lineItems.toList())

    override fun addProduct(product: Product, amount: Int) {
        if (product.id == null) {
            return
        }

        val index = findProduct(product.id)

        if (index < 0) {
            _lineItems.add(
                LineItem(
                    product.name,
                    product.price,
                    amount,
                    product.id,
                    product.deposit
                )
            )
            _lineItemsFlow.value = _lineItems.toList()
            return
        }

        _lineItems[index] = _lineItems[index].copy(amount = _lineItems[index].amount + amount)
        _lineItemsFlow.value = _lineItems.toList()
    }

    override fun removeProduct(productId: Int, amount: Int) {
        val index = findProduct(productId)

        if (index < 0) {
            return
        }

        val newAmount = _lineItems[index].amount - amount

        if (newAmount < 1) {
            _lineItems.removeAt(index)
            _lineItemsFlow.value = _lineItems.toList()
            return
        }

        _lineItems[index] = _lineItems[index].copy(amount = newAmount)
        _lineItemsFlow.value = _lineItems.toList()
    }

    override fun setProductAmount(productId: Int, amount: Int) {
        val index = findProduct(productId)

        if (index < 0) {
            return
        }

        if (amount < 1) {
            _lineItems.removeAt(index)
            _lineItemsFlow.value = _lineItems.toList()
            return
        }

        _lineItems[index] = _lineItems[index].copy(amount = amount)
        _lineItemsFlow.value = _lineItems.toList()
    }

    override fun removeProductCompletely(productId: Int) {
        val index = findProduct(productId)

        if (index < 0) {
            return
        }

        _lineItems.removeAt(index)
        _lineItemsFlow.value = _lineItems.toList()
    }

    override fun getTotal(): Double {
        return _lineItems.map { it.amount * it.pricePerUnit }.reduce { acc, value -> acc + value }
    }

    override fun getLineItems(): Flow<List<LineItem>> {
        return _lineItemsFlow
    }

    override fun clearCart() {
        _lineItems.clear()
        _lineItemsFlow.value = _lineItems.toList()
    }

    private fun findProduct(id: Int): Int {
        return _lineItems.indexOfFirst { it.productId == id }
    }
}