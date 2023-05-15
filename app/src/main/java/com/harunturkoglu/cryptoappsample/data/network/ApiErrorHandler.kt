package com.harunturkoglu.cryptoappsample.data.network

import android.content.Context
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.ext.orElse
import retrofit2.HttpException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(private val context: Context) {

    fun traceErrorException(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    400 -> BAD_REQUEST
                    401 -> UNAUTHORIZED
                    402 -> PAYMENT_REQUIRED
                    403 -> FORBIDDEN
                    429 -> TOO_MANY_REQUESTS
                    500 -> INTERNAL_SERVER_ERROR
                    else -> context.getString(R.string.unexpected_error).orElse()
                }
            }
            else -> {
                context.getString(R.string.unexpected_error).orElse()
            }
        }
    }

    private companion object ErrorStates {
        /**
        Errors that may occur given by documentation by CoinMarketCap
        https://coinmarketcap.com/api/documentation/v1/#section/Errors-and-Rate-Limits
         */
        /**
         * 400 (Bad Request) The server could not process the request, likely due to an invalid argument.
         */
        private const val BAD_REQUEST = "Bad Request!"

        /**
         * 401 (Unauthorized) Your request lacks valid authentication credentials, likely an issue with your API Key.
         */
        private const val UNAUTHORIZED = "Unauthorized!"

        /**
         * 402 (Payment Required) Your API request was rejected due to it being a paid subscription plan with an overdue balance.
         * Pay the balance in the Developer Portal billing tab and it will be enabled.
         */
        private const val PAYMENT_REQUIRED = "Payment Required!"

        /**
         * 403 (Forbidden) Your request was rejected due to a permission issue, likely a restriction on the API Key's associated service plan.
         * Here is a convenient map of service plans to endpoints.
         */
        private const val FORBIDDEN = "Forbidden!"

        /**
         * 429 (Too Many Requests) The API Key's rate limit was exceeded; consider slowing down your API Request frequency if this is an HTTP request throttling error.
         * Consider upgrading your service plan if you have reached your monthly API call credit limit for the day/month.
         */
        private const val TOO_MANY_REQUESTS = "Too Many Requests!"

        /**
         * 500 (Internal Server Error) An unexpected server issue was encountered.
         */
        private const val INTERNAL_SERVER_ERROR = "Internal Server Error!"
    }
}