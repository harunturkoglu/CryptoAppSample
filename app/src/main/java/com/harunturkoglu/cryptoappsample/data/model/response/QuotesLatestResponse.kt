package com.harunturkoglu.cryptoappsample.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyDetailUiModel
import com.harunturkoglu.cryptoappsample.ext.EMPTY
import com.harunturkoglu.cryptoappsample.ext.formatToUi
import com.harunturkoglu.cryptoappsample.ext.orElse
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import com.harunturkoglu.cryptoappsample.utils.KEY_ALGORITHM
import com.harunturkoglu.cryptoappsample.utils.UiString
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuotesLatestResponse(
    @SerializedName("status")
    val status: DetailStatus,
    @SerializedName("data")
    val data: DetailData,
) : Parcelable

@Parcelize
data class DetailStatus(
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("error_code")
    val error_code: Int?,
    @SerializedName("error_message")
    val error_message: String?,
    @SerializedName("elapsed")
    val elapsed: Int?,
    @SerializedName("credit_count")
    val credit_count: Int?,
    @SerializedName("notice")
    val notice: String?
) : Parcelable

@Parcelize
data class DetailData(
    @SerializedName("1")
    val id: CryptoDetailData?
) : Parcelable


@Parcelize
data class CryptoDetailData(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("num_market_pairs")
    val num_market_pairs: Int?,
    @SerializedName("date_added")
    val date_added: String?,
    @SerializedName("tags")
    val tags: List<CryptoDetailTag?>?,
    @SerializedName("max_supply")
    val max_supply: Long?,
    @SerializedName("circulating_supply")
    val circulating_supply: Double?,
    @SerializedName("total_supply")
    val total_supply: Double?,
    @SerializedName("is_active")
    val is_active: Int?,
    @SerializedName("infinite_supply")
    val infinite_supply: Boolean?,
    @SerializedName("platform")
    val platform: Platform?,
    @SerializedName("cmc_rank")
    val cmc_rank: Int?,
    @SerializedName("is_fiat")
    val is_fiat: Int?,
    @SerializedName("self_reported_circulating_supply")
    val self_reported_circulating_supply: Double?,
    @SerializedName("self_reported_market_cap")
    val self_reported_market_cap: Double?,
    @SerializedName("tvl_ratio")
    val tvl_ratio: Double?,
    @SerializedName("last_updated")
    val last_updated: String?,
    @SerializedName("quote")
    val quote: Quote?
) : Parcelable

@Parcelize
data class CryptoDetailTag(
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("category")
    val category: String?
) : Parcelable

fun QuotesLatestResponse?.toUiModel(dateHelper: DateHelper): CryptoCurrencyDetailUiModel =
    kotlin.run {
        CryptoCurrencyDetailUiModel(
            currencyNameSymbol = "${this?.data?.id?.name.orElse()} - ${this?.data?.id?.symbol.orElse()}",
            currencyTokenAddress = this?.data?.id?.platform?.token_address.orElse(),
            currencyMarketPair = this?.data?.id?.num_market_pairs?.orElse().toString(),
            currentPrice = UiString(R.string.text_detail_usd_format_with_symbol, this?.data?.id?.quote?.USD?.price.orElse().formatToUi().orElse()),
            currencyRank = this?.data?.id?.cmc_rank.orElse().toString(),
            currencyHashingAlgorithm = getAlgorithmByTags(this?.data?.id?.tags),
            dateAdded = with(dateHelper) { this@toUiModel?.data?.id?.date_added?.toDate().toStringDate().orElse()},
            priceChange1H = this?.data?.id?.quote?.USD?.percent_change_1h.orElse().toString(),
            priceChange24H = this?.data?.id?.quote?.USD?.percent_change_24h.orElse().toString(),
            priceChange7D = this?.data?.id?.quote?.USD?.percent_change_7d.orElse().toString(),
            priceChange30D = this?.data?.id?.quote?.USD?.percent_change_30d.orElse().toString(),
            priceChange60D = this?.data?.id?.quote?.USD?.percent_change_60d.orElse().toString(),
            priceChange90D = this?.data?.id?.quote?.USD?.percent_change_90d.orElse().toString(),
        )
    }

private fun getAlgorithmByTags(tags: List<CryptoDetailTag?>?): String {
    var algorithm = String.EMPTY
    tags?.forEach {
        it?.let {
            if (it.category == KEY_ALGORITHM)
                algorithm += if (algorithm.isEmpty())
                    it.name
                else
                    " - ${it.name}"
        }
    }
    return algorithm.ifEmpty { "-"}
}




