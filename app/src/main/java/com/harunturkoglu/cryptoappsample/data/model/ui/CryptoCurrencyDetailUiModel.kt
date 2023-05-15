package com.harunturkoglu.cryptoappsample.data.model.ui

import android.os.Parcelable
import com.harunturkoglu.cryptoappsample.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoCurrencyDetailUiModel(
    val currencyNameSymbol: String,
    val currencyTokenAddress: String,
    val currencyMarketPair: String,
    val currentPrice: UiString,
    val currencyRank: String,
    val currencyHashingAlgorithm: String,
    val dateAdded: String,
    val priceChange1H: String,
    val priceChange24H: String,
    val priceChange7D: String,
    val priceChange30D: String,
    val priceChange60D: String,
    val priceChange90D: String,
): Parcelable