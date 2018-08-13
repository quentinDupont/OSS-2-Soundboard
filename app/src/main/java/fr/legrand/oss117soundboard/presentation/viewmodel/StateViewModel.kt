package fr.legrand.oss117soundboard.presentation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class StateViewModel<T> : ViewModel() {
    protected abstract val currentViewState: T

    val viewState = MutableLiveData<T>()

    protected inline fun <reified T> MutableLiveData<T>.update(block: T.() -> Unit) {
        this.postValue((currentViewState as T).apply(block))
    }
}
