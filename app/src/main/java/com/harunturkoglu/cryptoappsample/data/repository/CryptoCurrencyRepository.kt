package com.harunturkoglu.cryptoappsample.data.repository

import com.harunturkoglu.cryptoappsample.data.api.CryptoCurrencyFactory
import com.harunturkoglu.cryptoappsample.data.model.response.toUiModel
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyUiModel
import com.harunturkoglu.cryptoappsample.data.network.ApiErrorHandler
import com.harunturkoglu.cryptoappsample.data.network.Resource
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoCurrencyRepository @Inject constructor(
    private val cryptocurrencyFactory: CryptoCurrencyFactory,
    private val apiErrorHandler: ApiErrorHandler,
    private val dateHelper: DateHelper
) : BaseRepository(apiErrorHandler = apiErrorHandler) {

    suspend fun getListingLatest(): Resource<List<CryptoCurrencyUiModel>> {
        return makeRequest {
            val result = cryptocurrencyFactory.getListingLatest()
            withContext(Dispatchers.Default) {
                result.toUiModel(dateHelper = dateHelper)
            }
        }
    }
}