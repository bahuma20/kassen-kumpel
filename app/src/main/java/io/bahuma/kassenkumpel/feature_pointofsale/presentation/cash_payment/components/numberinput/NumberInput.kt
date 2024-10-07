package io.bahuma.kassenkumpel.feature_pointofsale.presentation.cash_payment.components.numberinput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberInput(onValue: (Double) -> Unit) {
    var value by remember { mutableStateOf("") }

    fun add(i: Int) {
        if (value == "" && i == 0) {
            return
        }

        value += i.toString()

        onValue(toDouble(value))
    }

    fun remove() {
        value = value.dropLast(1)

        onValue(toDouble(value))
    }

    Column {
        Row {
            NumberInputButton(onClick = { add(1) }, label = "1", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(2) }, label = "2", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(3) }, label = "3", modifier = Modifier.weight(1f))
        }

        Row {
            NumberInputButton(onClick = { add(4) }, label = "4", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(5) }, label = "5", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(6) }, label = "6", modifier = Modifier.weight(1f))
        }

        Row {
            NumberInputButton(onClick = { add(7) }, label = "7", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(8) }, label = "8", modifier = Modifier.weight(1f))
            NumberInputButton(onClick = { add(9) }, label = "9", modifier = Modifier.weight(1f))
        }

        Row {
            NumberInputButton(
                onClick = { add(0); add(0) },
                label = "00",
                modifier = Modifier.weight(1f),
                withBorder = false
            )
            NumberInputButton(onClick = { add(0) }, label = "0", modifier = Modifier.weight(1f))
            NumberInputButton(
                onClick = { remove() },
                modifier = Modifier.weight(1f),
                withBorder = false
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Delete"
                )

            }

        }
    }
}

@Preview(showBackground = true, widthDp = 350)
@Composable
fun NumberInputPreview() {
    NumberInput({})
}

fun toDouble(value: String): Double {
    if (value.isEmpty()) {
        return 0.0
    }

    if (value.length == 1) {
        return ("0.0$value").toDouble()
    }

    if (value.length == 2) {
        return ("0.$value").toDouble()
    }

    return value.replaceRange(value.length - 2, value.length - 2, ".").toDouble()
}