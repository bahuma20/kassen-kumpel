package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.AddEditProductEvent
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.AddEditProductViewModel
import io.bahuma.kassenkumpel.feature_products.presentation.util.DecimalFormatter
import io.bahuma.kassenkumpel.feature_products.presentation.util.DecimalInputVisualTransformation
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditProductScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val nameState = viewModel.productName.value
    val priceState = viewModel.productPrice.value
    val depositState = viewModel.productDeposit.value
    val colorState = viewModel.productColor.value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditProductViewModel.UiEvent.SaveProduct -> {
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
                viewModel.onEvent(AddEditProductEvent.EnteredName(it))
            },
            label = {
                Text(text = "Name")
            }
        )

        Spacer(Modifier.height(spaceBetweenFields))

        OutlinedTextField(
            value = priceState,
            onValueChange = {
                viewModel.onEvent(
                    AddEditProductEvent.EnteredPrice(it)
                )
            },
            label = {
                Text(text = "Preis")
            },
            suffix = {
                Text(text = "€")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = DecimalInputVisualTransformation(decimalFormatter = DecimalFormatter())
        )

        Spacer(Modifier.height(spaceBetweenFields))

        OutlinedTextField(
            value = depositState,
            onValueChange = {
                viewModel.onEvent(
                    AddEditProductEvent.EnteredDeposit(it)
                )
            },
            label = {
                Text(text = "Pfand")
            },
            suffix = {
                Text(text = "€")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            visualTransformation = DecimalInputVisualTransformation(decimalFormatter = DecimalFormatter())
        )

        Spacer(Modifier.height(spaceBetweenFields))

        ColorPicker(
            "Farbe",
            colorState,
            onColorChanged = {
                viewModel.onEvent(AddEditProductEvent.ChangeColor(it))
            },
            modifier = Modifier.width(400.dp)
        )

        Spacer(Modifier.height(spaceBetweenFields))

        ExposedDropdownMenuBox(
            expanded = viewModel.categoryDropdownOpen.value,
            onExpandedChange = {
                if (it) {
                    viewModel.onEvent(AddEditProductEvent.OpenCategoryDropdown)
                } else {
                    viewModel.onEvent(AddEditProductEvent.CloseCategoryDropdown)
                }
            }
        ) {
            var value = "- Keine Kategorie -"

            try {
                value =
                    viewModel.categoryOptions.first { category -> category.categoryId == viewModel.productCategoryId.value }.name
            } catch (_: NoSuchElementException) {
            }

            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(text = "Kategorie")
                },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )

            ExposedDropdownMenu(
                expanded = viewModel.categoryDropdownOpen.value,
                onDismissRequest = { viewModel.onEvent(AddEditProductEvent.CloseCategoryDropdown) }
            ) {
                DropdownMenuItem(
                    text = { Text(text = "- Keine Kategorie -") },
                    onClick = { viewModel.onEvent(AddEditProductEvent.SelectedCategory(null)) },
                )

                for (category in viewModel.categoryOptions) {
                    DropdownMenuItem(
                        text = { Text(text = category.name) },
                        onClick = { viewModel.onEvent(AddEditProductEvent.SelectedCategory(category.categoryId)) },
                        leadingIcon = if (category.icon != null) {
                            {
                                Icon(
                                    imageVector = category.getCategoryIcon(),
                                    contentDescription = category.name
                                )
                            }
                        } else {
                            null
                        }
                    )
                }
            }
        }



        Spacer(Modifier.height(spaceBetweenFields))

        Button(onClick = {
            viewModel.onEvent(AddEditProductEvent.SaveProduct)
        }) {
            Text(text = "Speichern")
        }
    }
}