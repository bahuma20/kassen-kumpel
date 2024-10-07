package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import io.bahuma.kassenkumpel.AddEditCategoryScreen
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.model.CategoryIcon
import io.bahuma.kassenkumpel.feature_products.domain.model.InvalidCategoryException
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.CategoryUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _categoryName = mutableStateOf("")
    val categoryName: State<String> = _categoryName

    private val _categoryIcon: MutableState<CategoryIcon?> = mutableStateOf(null)
    val categoryIcon: State<CategoryIcon?> = _categoryIcon

    private var currentCategoryId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        val args = savedStateHandle.toRoute<AddEditCategoryScreen>()

        if (args.categoryId != null) {
            viewModelScope.launch {
                categoryUseCases.getCategory(args.categoryId)?.also { category ->
                    currentCategoryId = category.categoryId
                    _categoryName.value = category.name
                    _categoryIcon.value = category.icon
                }
            }
        }
    }

    fun onEvent(event: AddEditCategoryEvent) {
        when (event) {
            is AddEditCategoryEvent.ChangeIcon -> {
                _categoryIcon.value = event.value
            }

            is AddEditCategoryEvent.EnteredName -> {
                _categoryName.value = event.value
            }

            AddEditCategoryEvent.SaveCategory -> {
                viewModelScope.launch {
                    try {
                        categoryUseCases.addCategory(
                            Category(
                                name = categoryName.value,
                                icon = categoryIcon.value,
                                categoryId = currentCategoryId,
                            )
                        )

                        _eventFlow.emit(UiEvent.SaveCategory)
                    } catch (e: InvalidCategoryException) {
                        Log.e("AddEditCategoryViewModel", "Could not save category", e)
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data object SaveCategory : UiEvent()
    }
}