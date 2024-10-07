package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category.AddEditCategoryEvent
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category.AddEditCategoryViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditCategoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditCategoryViewModel = hiltViewModel()
) {
    val nameState = viewModel.categoryName.value
    val iconState = viewModel.categoryIcon.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditCategoryViewModel.UiEvent.SaveCategory -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val spaceBetweenFields = 12.dp

        OutlinedTextField(
            value = nameState,
            onValueChange = {
                viewModel.onEvent(AddEditCategoryEvent.EnteredName(it))
            },
            label = {
                Text(text = "Name")
            }
        )

        Spacer(Modifier.height(spaceBetweenFields))

        Button(onClick = {
            viewModel.onEvent(AddEditCategoryEvent.SaveCategory)
        }) {
            Text(text = "Speichern")
        }
    }
}