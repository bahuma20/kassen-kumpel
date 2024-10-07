package io.bahuma.kassenkumpel.feature_products.presentation.categories.components

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.bahuma.kassenkumpel.AddEditCategoryScreen
import io.bahuma.kassenkumpel.feature_products.presentation.categories.CategoriesEvent
import io.bahuma.kassenkumpel.feature_products.presentation.categories.CategoriesViewModel

@Composable
fun CategoriesScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AddEditCategoryScreen(null)) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier
                .fillMaxHeight()
                .widthIn(Dp.Unspecified, 600.dp)
                .padding(innerPadding),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.categories) { category ->
                CategoryItem(
                    category = category,
                    onEdit = { navController.navigate(AddEditCategoryScreen(category.categoryId)) },
                    onDelete = { viewModel.onEvent(CategoriesEvent.DeleteCategory(category)) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }

    if (state.categoryToDelete != null) {
        AlertDialog(
            icon = {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")

            },
            title = {
                Text(text = "Kategorie löschen")
            },
            text = {
                Text(buildAnnotatedString {
                    append("Möchtest du die Kategorie ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(state.categoryToDelete!!.name)
                    }
                    append(" wirklich löschen?")
                })
            },
            onDismissRequest = {
                viewModel.onEvent(CategoriesEvent.DeleteCategoryCancel)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CategoriesEvent.DeleteCategoryConfirm(state.categoryToDelete!!))
                    }
                ) {
                    Text(text = "Löschen")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(CategoriesEvent.DeleteCategoryCancel)
                    }
                ) {
                    Text(text = "Abbrechen")
                }
            }
        )

    }
}