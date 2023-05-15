package com.harunturkoglu.cryptoappsample.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.harunturkoglu.cryptoappsample.BuildConfig
import com.harunturkoglu.cryptoappsample.data.api.CryptoCurrencyFactory
import com.harunturkoglu.cryptoappsample.data.network.ApiErrorHandler
import com.harunturkoglu.cryptoappsample.data.network.AuthorizationInterceptor
import com.harunturkoglu.cryptoappsample.data.repository.CryptoCurrencyRepository
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val NETWORK_TIMEOUT_SECONDS = 30L


    @Provides
    @Singleton
    fun provideAuthorizationInterceptor() = AuthorizationInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if(BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        @ApplicationContext appContext: Context,
        authorizationInterceptor: AuthorizationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(ChuckerInterceptor.Builder(appContext).build())
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideCryptoCurrencyFactory(retrofit: Retrofit) =
        retrofit.create(CryptoCurrencyFactory::class.java)

}
