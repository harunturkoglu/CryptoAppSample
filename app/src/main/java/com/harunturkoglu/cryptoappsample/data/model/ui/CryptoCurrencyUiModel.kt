package com.harunturkoglu.cryptoappsample.data.model.ui

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.harunturkoglu.cryptoappsample.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoCurrencyUiModel(
    val currencySummary: CryptoCurrencySummaryUiModel
) : Parcelable

/*@Parcelize
data class CryptoCurrencyDetailUiModel(
    val volume24h: Double,
    val percentChange1Day: Double,
    val percentChange7Days: Double,
    val percentChange30Days: Double,
    val percentChange60Days: Double,
    val percentChange90Days: Double,
    val quoteLastUpdateDate: String,
    val tags: List<String>,
    val numMarketPairs: Int,
    val maxSupply: Long,
    val totalSupply: Double,
    val isInfiniteSupply: Boolean,
) : Parcelable*/

@Parcelize
data class CryptoCurrencySummaryUiModel(
val currencyId: Int,
val currencyNameSymbol: String,
val currencySlug: String,
val currencyPrice: UiString,
val currency24hChange: String,
@ColorRes
val currency24hChangeTextColorResId: Int,
val currencyRank: String,
@DrawableRes
val currency24hChangeDrawableResId: Int,
val lastUpdateDate: String): Parcelable