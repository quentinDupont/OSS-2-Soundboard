package fr.legrand.oss117soundboard.presentation.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import fr.legrand.oss117soundboard.presentation.utils.SingleLiveEvent
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

class ReplySharedViewModel @Inject constructor() : ViewModel() {

    val searchRequested = MutableLiveData<String>()
    val replyUpdated = MutableLiveData<Boolean>()

    fun onReplyUpdated() {
        replyUpdated.postValue(true)
    }

    companion object {
        const val NO_SEARCH = ""
    }
}
