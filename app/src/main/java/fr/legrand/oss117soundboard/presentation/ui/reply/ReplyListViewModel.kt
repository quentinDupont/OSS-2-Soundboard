package fr.legrand.oss117soundboard.presentation.ui.reply

import android.arch.lifecycle.MutableLiveData
import fr.legrand.oss117soundboard.data.repository.ContentRepository
import fr.legrand.oss117soundboard.presentation.component.MediaPlayerComponent
import fr.legrand.oss117soundboard.presentation.ui.reply.item.ReplyViewData
import fr.legrand.oss117soundboard.presentation.viewmodel.StateViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */
class ReplyListViewModel @Inject constructor(
        private val contentRepository: ContentRepository,
        private val mediaPlayerComponent: MediaPlayerComponent) : StateViewModel<ReplyListViewState>() {

    override val currentViewState = ReplyListViewState()

    private val disposable = CompositeDisposable()
    private val currentReplyList = mutableListOf<ReplyViewData>()

    val replyListLiveData = MutableLiveData<List<ReplyViewData>>()

    override fun onCleared() {
        disposable.clear()
    }

    fun getAllReplyWithSearch(search: String, fromFavorite: Boolean) {
        viewState.update {
            refreshing = true
        }
        contentRepository.getReplyWithSearch(search, fromFavorite).subscribeOn(Schedulers.io()).subscribeBy(
                onNext = {
                    currentReplyList.clear()
                    currentReplyList.addAll(it.map { ReplyViewData(it) })
                    replyListLiveData.postValue(currentReplyList.filter { it.isFavorite() == fromFavorite })
                    viewState.update {
                        displayingPlaceholder = it.isEmpty()
                        refreshing = false
                    }
                },
                onError = {
                    viewState.update {
                        displayingPlaceholder = true
                        refreshing = false
                    }
                }
        )
    }

    fun getAllReply(fromFavorite: Boolean) {
        viewState.update {
            refreshing = true
        }
        contentRepository.getAllReply(fromFavorite).subscribeOn(Schedulers.io()).subscribeBy(
                onNext = {
                    currentReplyList.clear()
                    currentReplyList.addAll(it.map { ReplyViewData(it) })
                    replyListLiveData.postValue(currentReplyList.filter { it.isFavorite() == fromFavorite })
                    viewState.update {
                        displayingPlaceholder = it.isEmpty()
                        refreshing = false
                    }

                },
                onError = {
                    viewState.update {
                        displayingPlaceholder = true
                        refreshing = false
                    }
                }

        )
    }

    fun updateFavoriteReply(replyId: Int, addToFavorite: Boolean) {
        contentRepository.updateFavoriteReply(replyId, addToFavorite).subscribeOn(Schedulers.io()).subscribe {
            val replyViewData = ReplyViewData(it)
            currentReplyList[currentReplyList.indexOfFirst { it.getId() == replyId }] = replyViewData
            replyListLiveData.postValue(currentReplyList)
        }
    }

    fun incrementReplyCount(replyId: Int) {
        contentRepository.incrementReplyListenCount(replyId).subscribeOn(Schedulers.io()).subscribe {
            listenToReply(replyId)
        }
    }

    private fun listenToReply(replyId: Int) {
        mediaPlayerComponent.playSoundMedia(replyId).subscribeOn(Schedulers.io()).subscribe {
            if (!mediaPlayerComponent.isPlayerCurrentlyRunning()) {
//                mainNavigatorListener.onReplyListened()
            }
        }
    }
}