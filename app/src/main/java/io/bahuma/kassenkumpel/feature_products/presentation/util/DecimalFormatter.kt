package io.bahuma.kassenkumpel.feature_products.presentation.util

import java.text.DecimalFormatSymbols

class DecimalFormatter(
    symbols: DecimalFormatSymbols = DecimalFormatSymbols.getInstance()
) {

    private val thousandsSeparator = symbols.groupingSeparator
    private val decimalSeparator = symbols.decimalSeparator
    private val minusSign = symbols.minusSign

    fun cleanup(input: String): String {

        if (input.matches("-".toRegex())) return "-"
        if (input.matches("^-?\\D".toRegex())) return ""
        if (input.matches("0+".toRegex())) return "0"

        val sb = StringBuilder()

        var hasDecimalSep = false

        for (char in input) {
            if (char.isDigit()) {
                sb.append(char)
                continue
            }

            if (char == decimalSeparator && !hasDecimalSep && sb.isNotEmpty()) {
                sb.append(char)
                hasDecimalSep = true
            }

            if (char == minusSign && sb.isEmpty()) {
                sb.append(char)
            }
        }

        return sb.toString()
    }

    fun formatForVisual(input: String): String {

        val isNegative = input.startsWith("-")

        val numberInput = if (isNegative) input.substring(1) else input

        val split = numberInput.split(decimalSeparator)

        val intPart = split[0]
            .reversed()
            .chunked(3)
            .joinToString(separator = thousandsSeparator.toString())
            .reversed()

        val fractionPart = split.getOrNull(1)

        return (if (isNegative) "-" else "") + if (fractionPart == null) intPart else intPart + decimalSeparator + fractionPart
    }

    fun fromDouble(value: Double): String {
        return value.toString().replace(".", ",")
    }

    fun toDouble(value: String): Double {
        return value.replace(",", ".").toDoubleOrNull() ?: throw Exception("Invalid value")
    }
}