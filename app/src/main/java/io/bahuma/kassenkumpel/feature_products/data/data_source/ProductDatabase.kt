package io.bahuma.kassenkumpel.feature_products.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import io.bahuma.kassenkumpel.core.model.Product
import io.bahuma.kassenkumpel.feature_products.domain.model.Category

@Database(
    entities = [Product::class, Category::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
    ]
)
abstract class ProductDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val categoryDao: CategoryDao

    companion object {
        const val DATABASE_NAME = "products_db"
    }
}