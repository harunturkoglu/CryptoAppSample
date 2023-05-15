package com.harunturkoglu.cryptoappsample.data.repository

import android.content.Context
import com.harunturkoglu.cryptoappsample.R
import com.harunturkoglu.cryptoappsample.data.network.Resource
import com.harunturkoglu.cryptoappsample.data.network.ApiErrorHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

abstract class BaseRepository constructor(
    private val apiErrorHandler: ApiErrorHandler
) {

    suspend fun <T> makeRequest(networkRequest: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(data = networkRequest())

            } catch (e: Exception) {
                Timber.e("ApiError:$e")
                Resource.Error(message = apiErrorHandler.traceErrorException(e))
            }
        }
    }
}

