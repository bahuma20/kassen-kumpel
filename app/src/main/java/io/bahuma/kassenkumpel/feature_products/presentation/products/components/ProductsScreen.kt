package io.bahuma.kassenkumpel.feature_products.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.AddEditProductsScreen
import io.bahuma.kassenkumpel.feature_products.presentation.products.ProductsEvent
import io.bahuma.kassenkumpel.feature_products.presentation.products.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AddEditProductsScreen(null))
                },
                Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxHeight()
                .widthIn(Dp.Unspecified, 600.dp)
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.products) { product ->
                ProductItem(
                    product,
                    onEdit = {
                        navController.navigate(
                            AddEditProductsScreen(
                                productId = product.id
                            )
                        )
                    },
                    onDelete = {
                        viewModel.onEvent(ProductsEvent.DeleteProduct(product))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

    if (state.productToDelete != null) {
        AlertDialog(
            icon = {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")

            },
            title = {
                Text(text = "Produkt löschen")
            },
            text = {
                Text(buildAnnotatedString {
                    append("Möchtest du das Produkt ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(state.productToDelete.name)
                    }
                    append(" wirklich löschen?")
                })
            },
            onDismissRequest = {

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(ProductsEvent.DeleteProductConfirm(state.productToDelete))
                    }
                ) {
                    Text(text = "Löschen")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(ProductsEvent.DeleteProductCancel)
                    }
                ) {
                    Text(text = "Abbrechen")
                }
            }
        )

    }


}