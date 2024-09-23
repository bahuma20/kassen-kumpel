package io.bahuma.kassenkumpel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bahuma.kassenkumpel.feature_pointofsale.data.repository.CartRepositoryImpl
import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.AddProductToCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.CartUseCases
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.ClearCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.GetCartTotal
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.GetLineItemsInCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.RemoveProductCompletelyFromCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.RemoveProductFromCart
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PointOfSaleModule {

    @Provides
    @Singleton
    fun provideCartRepository(): CartRepository {
        return CartRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideCartUseCases(
        cartRepository: CartRepository
    ): CartUseCases {
        return CartUseCases(
            addProductToCart = AddProductToCart(cartRepository),
            removeProductFromCart = RemoveProductFromCart(cartRepository),
            removeProductCompletelyFromCart = RemoveProductCompletelyFromCart(cartRepository),
            getLineItemsInCart = GetLineItemsInCart(cartRepository),
            getCartTotal = GetCartTotal(cartRepository),
            clearCart = ClearCart(cartRepository)
        )
    }
}