package com.harunturkoglu.cryptoappsample.data.network

import com.harunturkoglu.cryptoappsample.data.model.response.toUiModel

sealed class Resource<T>(val data:T? = null, val message: String? = null) {
    class Success<T>(data: T): Resource<T>(data = data)
    class Error<T>(message: String): Resource<T>(message = message)

    suspend fun onFinished(
        onSuccess: suspend (T) -> Unit,
        onFailure: suspend (String) -> Unit
    ) {
        when(this) {
            is Success -> onSuccess.invoke(data!!)
            is Error -> onFailure.invoke(message!!)
        }
    }
}