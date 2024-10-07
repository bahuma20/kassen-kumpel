package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cart.components.number_change_input

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberChangeInput(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onValueChange(value - 1)
            },
            enabled = value > 1,
        ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Minus")
        }

        Text(
            text = value.toString(),
            fontSize = 20.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        IconButton(onClick = {
            onValueChange(value + 1)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Plus")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberChangeInputPreview() {
    NumberChangeInput(4, {})
}

@Preview(showBackground = true)
@Composable
fun NumberChangeInputPreview2() {
    NumberChangeInput(1, {})
}