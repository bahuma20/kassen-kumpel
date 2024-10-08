package io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.bahuma.kassenkumpel.feature_products.domain.model.CategoryIcon

@Composable
fun IconPicker(
    label: String,
    icon: CategoryIcon?,
    onIconChanged: (CategoryIcon?) -> Unit,
    modifier: Modifier = Modifier
) {
    val size = 48.dp
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
                var icons: List<CategoryIcon?> = listOf(null)
                icons = icons + CategoryIcon.entries.toTypedArray()

                items(icons) { it ->
                    val borderSize = if (it == icon) 2.dp else 1.dp
                    val borderColor =
                        if (it == icon) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

                    val imageVector = it?.imageVector ?: Icons.Default.Block

                    IconButton(
                        onClick = { onIconChanged(it) },
                        modifier = Modifier
                            .border(borderSize, borderColor, CircleShape)
                    ) {
                        Icon(imageVector = imageVector, contentDescription = "Icon")
                    }

                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(16.dp, (-8).dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp, 0.dp)
        ) {
            Text(
                label,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconPickerPreview() {
    IconPicker("Icon", CategoryIcon.FOOD, {})
}

@Preview(showBackground = true)
@Composable
fun IconPickerPreview2() {
    IconPicker("Icon", null, {})
}