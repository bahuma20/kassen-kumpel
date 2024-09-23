package io.bahuma.kassenkumpel.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bahuma.kassenkumpel.feature_products.data.data_source.ProductDatabase
import io.bahuma.kassenkumpel.feature_products.data.repository.ProductRepositoryImpl
import io.bahuma.kassenkumpel.feature_products.domain.repository.ProductRepository
import io.bahuma.kassenkumpel.feature_products.domain.use_case.AddProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.DeleteProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.GetProduct
import io.bahuma.kassenkumpel.feature_products.domain.use_case.GetProducts
import io.bahuma.kassenkumpel.feature_products.domain.use_case.ProductUseCases
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
    fun provideProductUseCases(repository: ProductRepository): ProductUseCases {
        return ProductUseCases(
            getProducts = GetProducts(repository),
            getProduct = GetProduct(repository),
            deleteProduct = DeleteProduct(repository),
            addProduct = AddProduct(repository)
        )
    }
}