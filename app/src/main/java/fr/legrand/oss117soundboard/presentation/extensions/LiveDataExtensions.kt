package fr.legrand.oss117soundboard.presentation.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData

fun <T> LiveData<T>.observeSafe(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, android.arch.lifecycle.Observer<T> { t ->
        if (t != null) {
            observer(t)
        }
    })
}