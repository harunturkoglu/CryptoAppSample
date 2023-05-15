package com.harunturkoglu.cryptoappsample.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harunturkoglu.cryptoappsample.data.model.ui.CryptoCurrencyUiModel
import com.harunturkoglu.cryptoappsample.data.repository.CryptoCurrencyRepository
import com.harunturkoglu.cryptoappsample.ext.toLiveData
import com.harunturkoglu.cryptoappsample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    BaseViewModel() {

    private val _getListingLatestData: MutableLiveData<List<CryptoCurrencyUiModel>> =
        MutableLiveData()
    val getListingLatestData = _getListingLatestData.toLiveData()


    fun getLatest() {
        _loadingState.postValue(true)
        viewModelScope.launch {
            val result = cryptoCurrencyRepository.getListingLatest()
            result.onFinished(
                onSuccess = { data ->
                    _loadingState.postValue(false)
                    _getListingLatestData.postValue(data)
                },
                onFailure = {
                    _loadingState.postValue(false)
                    _errorState.emit(it)
                }
            )
        }
    }
}