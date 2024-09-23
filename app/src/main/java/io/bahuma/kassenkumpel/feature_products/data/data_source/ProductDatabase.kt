package io.bahuma.kassenkumpel.feature_products.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import io.bahuma.kassenkumpel.core.model.Product

@Database(
    entities = [Product::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class ProductDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

    companion object {
        const val DATABASE_NAME = "products_db"
    }
}