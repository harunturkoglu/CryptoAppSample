package com.harunturkoglu.cryptoappsample.data.api

import com.harunturkoglu.cryptoappsample.data.model.response.ListingLatestResponse
import com.harunturkoglu.cryptoappsample.data.model.response.QuotesLatestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCurrencyFactory {

    @GET(ENDPOINT_LISTING_LATEST)
    suspend fun getListingLatest(): ListingLatestResponse

    @GET(ENDPOINT_QUOTES_LATEST)
    suspend fun getQuotesLatest(@Query(QUERY_PARAMETER_ID) id: Int): QuotesLatestResponse


    private companion object {
        const val ENDPOINT_LISTING_LATEST = "v1/cryptocurrency/listings/latest"
        const val ENDPOINT_QUOTES_LATEST = "v2/cryptocurrency/quotes/latest"

        const val QUERY_PARAMETER_ID = "id"
    }
}