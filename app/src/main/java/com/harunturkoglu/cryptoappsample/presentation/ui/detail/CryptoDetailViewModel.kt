package com.harunturkoglu.cryptoappsample.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyDetailUiModel
import com.harunturkoglu.cryptoappsample.data.repository.CryptoCurrencyDetailRepository
import com.harunturkoglu.cryptoappsample.ext.EMPTY
import com.harunturkoglu.cryptoappsample.ext.INVALID_ID
import com.harunturkoglu.cryptoappsample.ext.orElse
import com.harunturkoglu.cryptoappsample.ext.toLiveData
import com.harunturkoglu.cryptoappsample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val cryptoCurrencyDetailRepository: CryptoCurrencyDetailRepository
) : BaseViewModel() {

    private val _getQuotesLatestData: MutableLiveData<CryptoCurrencyDetailUiModel> =
        MutableLiveData()
    val getQuotesLatestData = _getQuotesLatestData.toLiveData()

     var id = Int.INVALID_ID

    private var _refreshIntervalMillis = 5000L
    set(value) {
        refreshIntervalMillis = value.toString()
        field = value
    }
    var refreshIntervalMillis = String.EMPTY

    fun getLatest() {
        if(_loadingState.value.orElse())
            return

        _loadingState.postValue(true)
        viewModelScope.launch {
            val result = cryptoCurrencyDetailRepository.getQuotesLatest(id = id)
            result.onFinished(
                onSuccess = { data ->
                    _loadingState.postValue(false)
                    _getQuotesLatestData.postValue(data)
                },
                onFailure = {
                    _loadingState.postValue(false)
                    _errorState.emit(it)
                }
            )
        }
    }

    fun setRefreshIntervalValue(value: Long) {
        _refreshIntervalMillis = value
    }

    fun getRefreshIntervalValue() = _refreshIntervalMillis

}