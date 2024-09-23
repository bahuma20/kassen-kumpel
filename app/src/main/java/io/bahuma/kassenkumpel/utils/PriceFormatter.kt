package io.bahuma.kassenkumpel.utils

import android.icu.number.CurrencyPrecision
import android.icu.number.NumberFormatter
import android.icu.util.Currency
import java.util.Locale

fun formatPrice(value: Double): String {
    return NumberFormatter
        .withLocale(Locale.GERMANY)
        .unit(Currency.getInstance("EUR"))
        .precision(CurrencyPrecision.fixedFraction(2))
        .format(value)
        .toString()
}