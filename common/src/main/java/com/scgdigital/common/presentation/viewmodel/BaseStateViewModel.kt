package com.scgdigital.common.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scgdigital.common.data.executor.CoroutineDispatcherProvider
import com.scgdigital.common.data.executor.CoroutineDispatcherProviderImpl
import com.scgdigital.common.domain.listenable.Disposable
import com.scgdigital.common.presentation.livedata.SingleLiveEvent
import com.scgdigital.common.presentation.view.BackListener

/**
 * Base view model to have injected viewModelExecutor and common ViewState and ViewEvent
 */
abstract class BaseStateViewModel<VS : Any, VE : Any>(
    protected val dispatcherProvider: CoroutineDispatcherProvider = CoroutineDispatcherProviderImpl(),
    initialState: VS,
) : ViewModel(), BackListener {

    protected val compositeDisposable: MutableList<Disposable> = mutableListOf()

    protected val mutableState = MutableLiveData(initialState)
    val state: LiveData<VS> = mutableState
    val currentState get() = mutableState.value!!

    private val mutableEvent: SingleLiveEvent<VE> = SingleLiveEvent()
    val event: LiveData<VE> = mutableEvent

    protected fun postState(value: VS) {
        mutableState.postValue(value)
    }

    protected inline fun postState(builder: VS.() -> VS) {
        postState(currentState.builder())
    }

    protected fun setState(value: VS) {
        mutableState.value = value
    }

    inline fun setState(builder: VS.() -> VS) {
        `access$mutableState`.value = currentState.builder()
    }

    protected fun postEvent(value: VE) {
        mutableEvent.postValue(value)
    }

    protected fun setEvent(value: VE) {
        mutableEvent.value = value
    }

    override fun onCleared() {
        compositeDisposable.forEach {
            it.dispose()
        }
        super.onCleared()
    }

    @PublishedApi
    internal val `access$mutableState`: MutableLiveData<VS>
        get() = mutableState
}
