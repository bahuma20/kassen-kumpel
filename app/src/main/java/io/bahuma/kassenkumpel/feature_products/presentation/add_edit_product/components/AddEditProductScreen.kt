package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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

@Composable
fun AddEditProductScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {
    val nameState = viewModel.productName.value
    val priceState = viewModel.productPrice.value
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
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = nameState,
            onValueChange = {
                viewModel.onEvent(AddEditProductEvent.EnteredName(it))
            },
            label = {
                Text(text = "Name")
            }
        )

        Spacer(Modifier.height(8.dp))

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

        Spacer(Modifier.height(8.dp))

        ColorPicker(
            "Farbe",
            colorState,
            onColorChanged = {
                viewModel.onEvent(AddEditProductEvent.ChangeColor(it))
            },
            modifier = Modifier.width(400.dp)
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.onEvent(AddEditProductEvent.SaveProduct)
        }) {
            Text(text = "Speichern")
        }
    }
}