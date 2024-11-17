package io.bahuma.kassenkumpel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.bahuma.kassenkumpel.feature_pointofsale.data.repository.CartRepositoryImpl
import io.bahuma.kassenkumpel.feature_pointofsale.domain.repository.CartRepository
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment.CardPaymentUseCases
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment.Checkout
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.card_payment.PrepareCheckout
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.AddProductToCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.CartUseCases
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.ClearCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.GetCartTotal
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.GetLineItemsInCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.RemoveProductCompletelyFromCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.RemoveProductFromCart
import io.bahuma.kassenkumpel.feature_pointofsale.domain.use_case.cart.SetProductAmount
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
            setProductAmount = SetProductAmount(cartRepository),
            removeProductCompletelyFromCart = RemoveProductCompletelyFromCart(cartRepository),
            getLineItemsInCart = GetLineItemsInCart(cartRepository),
            getCartTotal = GetCartTotal(cartRepository),
            clearCart = ClearCart(cartRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCardPaymentUseCases(): CardPaymentUseCases {
        return CardPaymentUseCases(
            prepareCheckout = PrepareCheckout(),
            checkout = Checkout()
        )
    }
}
