package io.bahuma.kassenkumpel.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bahuma.kassenkumpel.feature_products.data.data_source.ProductDatabase
import io.bahuma.kassenkumpel.feature_products.data.repository.CategoryRepositoryImpl
import io.bahuma.kassenkumpel.feature_products.data.repository.ProductRepositoryImpl
import io.bahuma.kassenkumpel.feature_products.domain.repository.CategoryRepository
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.AddCategory
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.CategoryUseCases
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.DeleteCategory
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.GetCategories
import io.bahuma.kassenkumpel.feature_products.domain.use_case.category.GetCategory
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.AddProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.DeleteProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.GetProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.GetProducts
import io.bahuma.kassenkumpel.feature_products.domain.use_case.product.ProductUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            ProductDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImpl(db.productDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(db: ProductDatabase): CategoryRepository {
        return CategoryRepositoryImpl(db.categoryDao)
    }

    @Provides
    @Singleton
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(repository),
            getProduct = GetProduct(repository),
            deleteProduct = DeleteProduct(repository),
            addProduct = AddProduct(repository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(repository: CategoryRepository): CategoryUseCases {
        return CategoryUseCases(
            getCategories = GetCategories(repository),
            getCategory = GetCategory(repository),
            deleteCategory = DeleteCategory(repository),
            addCategory = AddCategory(repository)
        )
    }
}