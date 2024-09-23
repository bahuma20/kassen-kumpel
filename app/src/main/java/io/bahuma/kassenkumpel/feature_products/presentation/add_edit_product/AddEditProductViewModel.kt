package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.AddEditProductsScreen
import io.bahuma.kassenkumpel.core.model.InvalidProductException
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.core.model.ProductColor
import io.bahuma.kassenkumpel.feature_products.domain.use_case.ProductUseCases
import io.bahuma.kassenkumpel.feature_products.presentation.util.DecimalFormatter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _productName = mutableStateOf("")
    val productName: State<String> = _productName

    private val _productPrice = mutableStateOf("")
    val productPrice: State<String> = _productPrice

    private val _productColor = mutableIntStateOf(Random.nextInt(0, ProductColor.entries.size))
    val productColor: State<Int> = _productColor

    private var currentProductId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val decimalFormatter = DecimalFormatter()

    init {
        val args = savedStateHandle.toRoute<AddEditProductsScreen>()

        if (args.productId != null) {
            viewModelScope.launch {
                productUseCases.getProduct(args.productId)?.also { product ->
                    currentProductId = product.id
                    _productName.value = product.name
                    _productPrice.value = decimalFormatter.fromDouble(product.price)
                    _productColor.intValue = product.color
                }
            }
        }
    }

    fun onEvent(event: AddEditProductEvent) {
        when (event) {
            is AddEditProductEvent.EnteredName -> {
                _productName.value = event.value
            }

            is AddEditProductEvent.ChangeColor -> {
                _productColor.intValue = event.value
            }

            is AddEditProductEvent.EnteredPrice -> {
                _productPrice.value = decimalFormatter.cleanup(event.value)
            }

            AddEditProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {
                        productUseCases.addProduct(
                            Product(
                                name = productName.value,
                                price = decimalFormatter.toDouble(productPrice.value),
                                color = productColor.value,
                                id = currentProductId
                            )
                        )

                        _eventFlow.emit(UiEvent.SaveProduct)
                    } catch (e: InvalidProductException) {
                        Log.e("AddEditProductViewModel", "Could not save product", e)
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveProduct : UiEvent()
    }
}