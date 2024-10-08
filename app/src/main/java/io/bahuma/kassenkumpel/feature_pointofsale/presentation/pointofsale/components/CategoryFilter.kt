package io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.bahuma.kassenkumpel.feature_products.domain.model.Category
import io.bahuma.kassenkumpel.feature_products.domain.model.CategoryIcon

@Composable
fun CategoryFilter(
    categories: List<Category>,
    selectedCategory: Category?,
    onSelectedCategory: (Category?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        FilterChip(
            onClick = { onSelectedCategory(null) },
            selected = selectedCategory == null,
            label = {
                Text(text = "Alle Produkte")
            },
            modifier = Modifier.padding(8.dp, 0.dp)
        )

        for (category in categories) {
            FilterChip(
                onClick = { onSelectedCategory(category) },
                selected = selectedCategory == category,
                label = {
                    Text(text = category.name)
                },
                leadingIcon = if (category.icon != null) {
                    {
                        Icon(
                            imageVector = category.getCategoryIcon(),
                            contentDescription = category.name
                        )
                    }

                } else {
                    null
                },
                modifier = Modifier.padding(8.dp, 0.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CategoryFilterPreview() {
    CategoryFilter(
        categories = listOf(
            Category("Essen", CategoryIcon.FOOD), Category("Trinken", CategoryIcon.DRINKS)
        ), selectedCategory = Category("Essen", CategoryIcon.FOOD)
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryFilterPreview2() {
    CategoryFilter(
        categories = listOf(
            Category("Essen", CategoryIcon.FOOD), Category("Trinken", CategoryIcon.DRINKS)
        ), selectedCategory = null
    )
}