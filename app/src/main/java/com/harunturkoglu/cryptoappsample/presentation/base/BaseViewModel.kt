package com.harunturkoglu.cryptoappsample.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harunturkoglu.cryptoappsample.ext.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState.toLiveData()

    protected val _errorState = MutableSharedFlow<String>()
    val errorState = _errorState.asSharedFlow()


    private var job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}