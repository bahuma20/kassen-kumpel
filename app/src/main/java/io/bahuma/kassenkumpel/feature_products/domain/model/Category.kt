package io.bahuma.kassenkumpel.feature_products.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import io.bahuma.kassenkumpel.core.model.Product

@Entity
data class Category(
    val name: String,
    val icon: CategoryIcon? = null,
    val weight: Int = 0,
    val deleted: Boolean = false,
    @PrimaryKey val categoryId: Int? = null,
) {
    fun getCategoryIcon(): ImageVector {
        return icon?.imageVector ?: Icons.Default.Folder
    }
}

enum class CategoryIcon(val imageVector: ImageVector) {
    DRINKS(Icons.Default.LocalDrink),
    FOOD(Icons.Default.Restaurant)
}

class InvalidCategoryException(message: String) : Exception(message)

data class CategoryWithProducts(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val products: List<Product>

)