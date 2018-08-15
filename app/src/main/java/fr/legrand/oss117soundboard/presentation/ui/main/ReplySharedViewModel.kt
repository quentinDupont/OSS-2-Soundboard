package fr.legrand.oss117soundboard.presentation.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

class ReplySharedViewModel @Inject constructor() : ViewModel() {

    val searchRequested = MutableLiveData<String>()

    companion object {
        const val NO_SEARCH = ""
    }
}
