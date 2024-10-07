package io.bahuma.kassenkumpel.feature_pointofsale.presentation.products.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(
    products: List<io.bahuma.kassenkumpel.core.model.Product>,
    itemsInCart: Map<Int, Int>,
    onItemAdded: (io.bahuma.kassenkumpel.core.model.Product) -> Unit,
    onItemRemoved: (io.bahuma.kassenkumpel.core.model.Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val itemCardWidth = 135.dp
    val gap = 16.dp

    LazyVerticalGrid(
        columns = GridCells.FixedSize(itemCardWidth + gap),
        contentPadding = PaddingValues(gap),
        horizontalArrangement = Arrangement.spacedBy(gap),
        verticalArrangement = Arrangement.spacedBy(gap),
        modifier = modifier
    ) {
        items(products) { product ->
            ProductCard(
                product.name,
                product.getProductColor(),
                product.price,
                itemsInCart[product.id] ?: 0,
                modifier = Modifier
                    .combinedClickable(
                        onClick = { onItemAdded(product) },
                        onLongClick = { onItemRemoved(product) }
                    )
            )

        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun ProductGridPreview() {
    val products = listOf(
        io.bahuma.kassenkumpel.core.model.Product(
            "Leberkässemmel",
            2.5,
            Color.Red.toArgb(),
            null,
            false,
            1
        ),
        io.bahuma.kassenkumpel.core.model.Product("Kaffee", 2.5, Color.Blue.toArgb(), null,false, 2),
        io.bahuma.kassenkumpel.core.model.Product("Kuchen", 2.5, Color.Blue.toArgb(), null,false, 3),
        io.bahuma.kassenkumpel.core.model.Product("Wasser", 2.5, Color.Green.toArgb(), null,false, 4),
        io.bahuma.kassenkumpel.core.model.Product("Spezi", 2.5, Color.Green.toArgb(), null,false, 5),
        io.bahuma.kassenkumpel.core.model.Product("Bier", 2.5, Color.Green.toArgb(), null,false, 6),
    )

    val productsInCart = hashMapOf(Pair(2, 5), Pair(3, 1), Pair(6, 10))

    ProductGrid(products, productsInCart, { }, { })
}

