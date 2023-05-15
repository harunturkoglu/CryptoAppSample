package com.harunturkoglu.cryptoappsample.data.repository

import com.harunturkoglu.cryptoappsample.data.api.CryptoCurrencyFactory
import com.harunturkoglu.cryptoappsample.data.model.response.toUiModel
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyDetailUiModel
import com.harunturkoglu.cryptoappsample.data.network.ApiErrorHandler
import com.harunturkoglu.cryptoappsample.data.network.Resource
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoCurrencyDetailRepository @Inject constructor(
    private val cryptocurrencyFactory: CryptoCurrencyFactory,
    private val apiErrorHandler: ApiErrorHandler,
    private val dateHelper: DateHelper
) : BaseRepository(apiErrorHandler = apiErrorHandler) {

    suspend fun getQuotesLatest(id: Int): Resource<CryptoCurrencyDetailUiModel> {
        return makeRequest {
            val result = cryptocurrencyFactory.getQuotesLatest(id = id)
            withContext(Dispatchers.Main) {
                result.toUiModel(dateHelper = dateHelper)
            }
        }
    }
}