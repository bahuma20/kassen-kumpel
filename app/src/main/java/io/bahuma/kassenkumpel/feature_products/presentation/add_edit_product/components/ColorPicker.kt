package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import io.bahuma.kassenkumpel.core.model.ProductColor

@Composable
fun ColorPicker(
    label: String,
    color: Int,
    onColorChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val size = 36.dp
    val gap = 8.dp
    Box(modifier = modifier.padding(top = 8.dp)) {
        Box(
            Modifier
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                .padding(12.dp, 12.dp, 12.dp, 12.dp)
                .defaultMinSize(
                    minWidth = OutlinedTextFieldDefaults.MinWidth
                )
        ) {

            LazyVerticalGrid(
                columns = GridCells.FixedSize(size),
                verticalArrangement = Arrangement.spacedBy(gap),
                horizontalArrangement = Arrangement.spacedBy(gap),
                modifier = modifier
            ) {
                itemsIndexed(ProductColor.entries) { index, it ->
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                            .background(it.color)
                            .clickable {
                                onColorChanged(index)
                            }
                    ) {
                        if (index == color) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                tint = Color.White,
                                contentDescription = "Ausgewählt",
                                modifier = Modifier
                                    .size(size / 1.5f)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }



        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(16.dp, -8.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp, 0.dp)
        ) {
            Text(
                "Farbe",
                color = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            )
        }
    }


}

@Preview(showBackground = true, widthDp = 600)
@Composable
fun ColorPickerPreview() {
    Box(modifier = Modifier.padding(20.dp)) {
        ColorPicker(label = "Farbe", color = 2, onColorChanged = { })
    }
}