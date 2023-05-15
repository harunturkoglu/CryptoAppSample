package com.harunturkoglu.cryptoappsample.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.harunturkoglu.cryptoappsample.data.api.CryptoCurrencyFactory
import com.harunturkoglu.cryptoappsample.data.local.LocalDataManager
import com.harunturkoglu.cryptoappsample.data.network.ApiErrorHandler
import com.harunturkoglu.cryptoappsample.data.repository.CryptoCurrencyDetailRepository
import com.harunturkoglu.cryptoappsample.data.repository.CryptoCurrencyRepository
import com.harunturkoglu.cryptoappsample.utils.DateHelper
import com.harunturkoglu.cryptoappsample.utils.SHARED_PREFERENCES_FILE_NAME
import com.harunturkoglu.cryptoappsample.utils.SharedPreferencesManager
import com.harunturkoglu.cryptoappsample.utils.FirebaseAuthenticationManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Provides
    @Singleton
    fun provideApiErrorHandler(@ApplicationContext context: Context) = ApiErrorHandler(context)

    @Provides
    fun provideDateHelper() = DateHelper()

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPreferencesManager(
        sharedPreferences: SharedPreferences,
        gson: Gson
    ): SharedPreferencesManager =
        SharedPreferencesManager(sharedPreferences = sharedPreferences, gson)

    @Singleton
    @Provides
    fun provideLocalDataManager(sharedPreferencesManager: SharedPreferencesManager) =
        LocalDataManager(sharedPreferencesManager = sharedPreferencesManager)

    @Singleton
    @Provides
    fun provideFirebaseAuthenticationManager(localDataManager: LocalDataManager) = FirebaseAuthenticationManager(localDataManager = localDataManager)

    @Provides
    @Singleton
    fun provideCryptoCurrencyRepository(
        @ApplicationContext context: Context,
        cryptocurrencyFactory: CryptoCurrencyFactory,
        apiErrorHandler: ApiErrorHandler,
        dateHelper: DateHelper
    ) = CryptoCurrencyRepository(
        cryptocurrencyFactory = cryptocurrencyFactory,
        apiErrorHandler = apiErrorHandler,
        dateHelper = dateHelper
    )

    @Provides
    @Singleton
    fun provideCryptoCurrencyDetailRepository(
        @ApplicationContext context: Context,
        cryptocurrencyFactory: CryptoCurrencyFactory,
        apiErrorHandler: ApiErrorHandler,
        dateHelper: DateHelper
    ) = CryptoCurrencyDetailRepository(
        cryptocurrencyFactory = cryptocurrencyFactory,
        apiErrorHandler = apiErrorHandler,
        dateHelper = dateHelper
    )
}