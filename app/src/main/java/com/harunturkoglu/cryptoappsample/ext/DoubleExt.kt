package com.harunturkoglu.cryptoappsample.ext

import com.harunturkoglu.cryptoappsample.utils.CURRENCY_UI_FORMAT
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Double?.orElse(value: Double = 0.0) = this ?: value

private val decimalFormatSymbol: DecimalFormatSymbols by lazy {
    DecimalFormatSymbols()
}
private val uiCurrencyFormat: DecimalFormat by lazy {
    decimalFormatSymbol.decimalSeparator = ','
    decimalFormatSymbol.groupingSeparator = '.'
    DecimalFormat(CURRENCY_UI_FORMAT, decimalFormatSymbol)
}

fun Double.formatToUi(): String? {
    return uiCurrencyFormat.format(this)
}

