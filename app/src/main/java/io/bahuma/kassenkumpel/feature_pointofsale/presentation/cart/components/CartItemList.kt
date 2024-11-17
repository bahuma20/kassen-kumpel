package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.bahuma.kassenkumpel.feature_pointofsale.domain.model.LineItem
import kotlinx.coroutines.launch

@Composable
fun CartItemList(
    lineItems: List<LineItem>,
    onRemove: (LineItem) -> Unit,
    onClickLineItem: (LineItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lineItems.count()) {
        coroutineScope.launch {
            listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
        }
    }

    if (lineItems.isEmpty()) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Artikel oder Betrag hinzufügen"
            )
        }

    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier,
            state = listState
        ) {
            itemsIndexed(
                items = lineItems,
                key = { _, lineItem -> lineItem.productId ?: 0 }
            ) { _, lineItem ->
                CartItemRow(
                    lineItem,
                    onRemove = {
                        onRemove(lineItem)
                    },
                    modifier = Modifier.clickable { onClickLineItem(lineItem) }
                )
            }
        }
    }

}

@Preview(widthDp = 300, showBackground = true, backgroundColor = 1)
@Composable
fun CartItemListPreview() {
    val lineItems = listOf(
        LineItem("Kaffee", 2.5, 1, 1, null),
        LineItem("Kuchen", 3.0, 10, 2, null),
    )

    CartItemList(lineItems, {}, {})
}