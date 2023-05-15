package com.harunturkoglu.cryptoappsample.data.network

import com.harunturkoglu.cryptoappsample.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor: Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header(HEADER_KEY, BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }

    private companion object{
        const val HEADER_KEY = "X-CMC_PRO_API_KEY"
    }
}