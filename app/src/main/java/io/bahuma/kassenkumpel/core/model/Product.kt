package io.bahuma.kassenkumpel.core.model

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    val name: String,
    val price: Double,
    val color: Int,
    @ColumnInfo(defaultValue = "null") val categoryId: Int? = null,
    @ColumnInfo(defaultValue = "false") val deleted: Boolean = false,
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(defaultValue = "null") val deposit: Double? = null,
) {
    fun getProductColor(): Color {
        if (color >= 0 && color < ProductColor.entries.size) {
            return ProductColor.entries[color].color
        }

        return Color.Black
    }
}

enum class ProductColor(val color: Color) {
    CARIBBEAN_CURRENT(Color(21, 96, 100)),
    MINT(Color(0, 196, 154)),
    NAPLES_YELLOW(Color(248, 225, 108)),
    MELON(Color(255, 194, 180)),
    CORAL(Color(251, 143, 103)),
    MEDIUM_SLATE_BLUE(Color(118, 120, 237)),
    RASPBERRY_ROSE(Color(181, 68, 110))
}

class InvalidProductException(message: String) : Exception(message)