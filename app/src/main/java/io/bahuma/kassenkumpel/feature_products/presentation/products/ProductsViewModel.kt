package io.bahuma.kassenkumpel.feature_products.presentation.products

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.feature_products.domain.use_case.ProductUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProductsState())
    val state: State<ProductsState> = _state

    private var getProductsJob: Job? = null

    init {
        getProducts()
    }

    fun onEvent(event: ProductsEvent) {
        when (event) {
            is ProductsEvent.DeleteProduct -> {
                _state.value = state.value.copy(
                    productToDelete = event.product
                )
            }

            is ProductsEvent.DeleteProductConfirm -> {
                viewModelScope.launch {
                    productUseCases.deleteProduct(event.product)
                }
                _state.value = state.value.copy(
                    productToDelete = null
                )
            }

            ProductsEvent.DeleteProductCancel -> {
                _state.value = state.value.copy(
                    productToDelete = null
                )
            }
        }
    }

    private fun getProducts() {
        getProductsJob?.cancel()

        getProductsJob = productUseCases.getProducts()
            .onEach { products ->
                _state.value = state.value.copy(
                    products = products
                )
            }
            .launchIn(viewModelScope)
    }
}