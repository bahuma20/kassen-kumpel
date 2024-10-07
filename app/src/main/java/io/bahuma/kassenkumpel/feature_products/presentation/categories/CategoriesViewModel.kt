package io.bahuma.kassenkumpel.feature_products.presentation.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.CategoryUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val useCases: CategoryUseCases
) : ViewModel() {
    private val _state = mutableStateOf(CategoriesState())
    val state: State<CategoriesState> = _state

    private var getCategoriesJob: Job? = null

    init {
        getCategories()
    }

    fun onEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.DeleteCategory -> {
                _state.value = state.value.copy(
                    categoryToDelete = event.category
                )
            }

            CategoriesEvent.DeleteCategoryCancel -> {
                _state.value = state.value.copy(
                    categoryToDelete = null
                )
            }

            is CategoriesEvent.DeleteCategoryConfirm -> {
                viewModelScope.launch {
                    useCases.deleteCategory(event.category)
                }
                _state.value = state.value.copy(
                    categoryToDelete = null
                )
            }
        }
    }

    private fun getCategories() {
        getCategoriesJob?.cancel()

        getCategoriesJob = useCases.getCategories()
            .onEach { categories ->
                _state.value = state.value.copy(categories = categories)
            }
            .launchIn(viewModelScope)
    }
}