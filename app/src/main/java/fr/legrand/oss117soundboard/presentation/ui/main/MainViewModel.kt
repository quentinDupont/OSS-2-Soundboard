package fr.legrand.oss117soundboard.presentation.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import fr.legrand.oss117soundboard.data.repository.ContentRepository
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

class MainViewModel @Inject constructor(
        private val contentRepository: ContentRepository,
        private val mediaPlayerComponent: MediaPlayerComponent
) : ViewModel() {

    val replyLoaded = MutableLiveData<Boolean>()
    private var replyAlreadyLoaded = false

    init {
        initAllReply()
    }


    private fun initAllReply() {
        if (!replyAlreadyLoaded) {
            replyAlreadyLoaded = true
            contentRepository.initAllReply().subscribeOn(Schedulers.io())
                    .subscribeBy(onComplete = {
                        replyLoaded.postValue(replyAlreadyLoaded)
                    }, onError = {
                        replyAlreadyLoaded = false
                    })
        }
    }

    fun releaseRunningPlayers() {
        mediaPlayerComponent.releaseAllRunningPlayer()
    }

    override fun onCleared() {
        super.onCleared()
        releaseRunningPlayers()
    }
}
