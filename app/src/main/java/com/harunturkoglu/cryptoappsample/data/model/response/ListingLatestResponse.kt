package com.harunturkoglu.cryptoappsample.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyUiModel
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencySummaryUiModel
import com.harunturkoglu.cryptoappsample.ext.INVALID_ID
import com.harunturkoglu.cryptoappsample.ext.formatToUi
import com.harunturkoglu.cryptoappsample.ext.orElse
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import com.harunturkoglu.cryptoappsample.utils.UiString
import kotlinx.parcelize.Parcelize

/**
 * Explanation of Parameter Terms.
 *
 * https://coinmarketcap.com/api/documentation/v1/#operation/getV1CryptocurrencyListingsLatest
 *
market_cap: CoinMarketCap's market cap rank as outlined in our methodology.
market_cap_strict: A strict market cap sort (latest trade price x circulating supply).
name: The cryptocurrency name.
symbol: The cryptocurrency symbol.
date_added: Date cryptocurrency was added to the system.
price: latest average trade price across markets.
circulating_supply: approximate number of coins currently in circulation.
total_supply: approximate total amount of coins in existence right now (minus any coins that have been verifiably burned).
max_supply: our best approximation of the maximum amount of coins that will ever exist in the lifetime of the currency.
num_market_pairs: number of market pairs across all exchanges trading each currency.
market_cap_by_total_supply_strict: market cap by total supply.
volume_24h: rolling 24 hour adjusted trading volume.
volume_7d: rolling 24 hour adjusted trading volume.
volume_30d: rolling 24 hour adjusted trading volume.
percent_change_1h: 1 hour trading price percentage change for each currency.
percent_change_24h: 24 hour trading price percentage change for each currency.
percent_change_7d: 7 day trading price percentage change for each currency.
 */
@Parcelize
data class ListingLatestResponse(
    @SerializedName("data")
    val data: List<CryptoCurrency?>?,
    @SerializedName("status")
    val status: Status?
) : Parcelable

@Parcelize

data class CryptoCurrency(
    @SerializedName("circulating_supply")
    val circulating_supply: Double?,
    @SerializedName("cmc_rank")
    val cmc_rank: Int?,
    @SerializedName("date_added")
    val date_added: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("infinite_supply")
    val infinite_supply: Boolean?,
    @SerializedName("last_updated")
    val last_updated: String?,
    @SerializedName("max_supply")
    val max_supply: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("num_market_pairs")
    val num_market_pairs: Int?,
    @SerializedName("platform")
    val platform: Platform?,
    @SerializedName("quote")
    val quote: Quote?,
    @SerializedName("self_reported_circulating_supply")
    val self_reported_circulating_supply: Double?,
    @SerializedName("self_reported_market_cap")
    val self_reported_market_cap: Double?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("tags")
    val tags: List<String?>?,
    @SerializedName("total_supply")
    val total_supply: Double?,
    @SerializedName("tvl_ratio")
    val tvl_ratio: Double?,
) : Parcelable

@Parcelize
data class Platform(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("token_address")
    val token_address: String?
) : Parcelable

@Parcelize
data class Quote(
    @SerializedName("USD")
    val USD: USD?
) : Parcelable

@Parcelize
data class USD(
    @SerializedName("fully_diluted_market_cap")
    val fully_diluted_market_cap: Double?,
    @SerializedName("last_updated")
    val last_updated: String?,
    @SerializedName("market_cap")
    val market_cap: Double?,
    @SerializedName("market_cap_dominance")
    val market_cap_dominance: Double?,
    @SerializedName("percent_change_1h")
    val percent_change_1h: Double?,
    @SerializedName("percent_change_24h")
    val percent_change_24h: Double?,
    @SerializedName("percent_change_30d")
    val percent_change_30d: Double?,
    @SerializedName("percent_change_60d")
    val percent_change_60d: Double,
    @SerializedName("percent_change_7d")
    val percent_change_7d: Double?,
    @SerializedName("percent_change_90d")
    val percent_change_90d: Double?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("tvl")
    val tvl: Double?,
    @SerializedName("volume_24h")
    val volume_24h: Double?,
    @SerializedName("volume_change_24h")
    val volume_change_24h: Double?
) : Parcelable

@Parcelize
data class Status(
    @SerializedName("credit_count")
    val credit_count: Int?,
    @SerializedName("elapsed")
    val elapsed: Int?,
    @SerializedName("error_code")
    val error_code: Int?,
    @SerializedName("error_message")
    val error_message: String?,
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("total_count")
    val total_count: Int?,
    @SerializedName("notice")
    val notice: String?
) : Parcelable

fun ListingLatestResponse?.toUiModel(dateHelper: DateHelper): List<CryptoCurrencyUiModel> =
    this?.data?.map {
        it.toUiModel(dateHelper = dateHelper)
    } ?: emptyList()

/**
 * CryptoCurrencyUiModel class represents the Summary and Detail part of the cryptocurrency.
 * Summary part is shown in the list contains in Homepage, and the detail part is shown in the Detail Screen.
 */
private fun CryptoCurrency?.toUiModel(dateHelper: DateHelper) = kotlin.run {
    CryptoCurrencyUiModel(
       currencySummary = this.toCryptoCurrencySummaryUiModel(dateHelper = dateHelper)
    )
}

/**
 * Summary data of the cryptocurrency, this data will shown in Homepage
 */
private fun CryptoCurrency?.toCryptoCurrencySummaryUiModel(dateHelper: DateHelper) = kotlin.run{
    val isVolumeIncreasedIn24H = this?.quote?.USD?.volume_change_24h.orElse() > 0
    CryptoCurrencySummaryUiModel(
        currencyId = this?.id.orElse(Int.INVALID_ID),
        currencyNameSymbol = "${this?.name.orElse()} - ${this?.symbol.orElse()}",
        currencySlug = this?.slug.orElse(),
        currencyPrice = UiString(R.string.txt_cryptocurrency_usd_format, (this?.quote?.USD?.price?.orElse()?.formatToUi()).toString()),
        currency24hChange = this?.quote?.USD?.volume_change_24h.orElse().toString(),
        currency24hChangeTextColorResId = if(isVolumeIncreasedIn24H) R.color.jungle_green else R.color.lava,
        currency24hChangeDrawableResId = if(isVolumeIncreasedIn24H) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down,
        lastUpdateDate = with(dateHelper) {
            this@toCryptoCurrencySummaryUiModel?.last_updated?.toDate().toStringDate().orElse()
        },
        currencyRank = this?.cmc_rank.orElse().toString()
    )
}

/**
 * Details data of the cryptocurrency, this data will shown in Detail Screen
 */
/*private fun CryptoCurrency?.toCryptoCurrencyDetailUiModel(dateHelper: DateHelper): CryptoCurrencyDetailUiModel =  with(this?.quote?.USD) {
    CryptoCurrencyDetailUiModel(
        volume24h = this?.volume_24h.orElse(),
        percentChange1Day = this?.percent_change_24h.orElse(),
        percentChange7Days = this?.percent_change_7d.orElse(),
        percentChange30Days = this?.percent_change_30d.orElse(),
        percentChange60Days = this?.percent_change_60d.orElse(),
        percentChange90Days = this?.percent_change_90d.orElse(),
        quoteLastUpdateDate = with(dateHelper) {
            this@toCryptoCurrencyDetailUiModel?.last_updated?.toDate().toUiDate().orElse()
        },
        tags =  this@toCryptoCurrencyDetailUiModel?.tags.orEmpty().map { it.orElse() },
        numMarketPairs = this@toCryptoCurrencyDetailUiModel?.num_market_pairs.orElse(Int.INVALID_ID),
        maxSupply = this@toCryptoCurrencyDetailUiModel?.max_supply.orElse(),
        totalSupply = this@toCryptoCurrencyDetailUiModel?.total_supply.orElse(),
        isInfiniteSupply = this@toCryptoCurrencyDetailUiModel?.infinite_supply.orElse(),
    )
}*/
