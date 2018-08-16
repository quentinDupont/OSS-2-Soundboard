package fr.legrand.oss117soundboard.data.repository

import android.content.Context
import fr.legrand.oss117soundboard.R
import fr.legrand.oss117soundboard.data.entity.Reply
import fr.legrand.oss117soundboard.data.manager.db.DatabaseManager
import fr.legrand.oss117soundboard.data.manager.file.FileManager
import fr.legrand.oss117soundboard.data.manager.sharedpref.SharedPrefManager
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.ThreadLocalRandom
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Benjamin on 30/09/2017.
 */
@Singleton
class ContentRepositoryImpl @Inject constructor(
        private val databaseManager: DatabaseManager,
        private val sharedPrefManager: SharedPrefManager,
        private val fileManager: FileManager,
        private val context: Context
) : ContentRepository {

    override fun getReplyWithSearch(search: String, fromFavorite: Boolean): Observable<List<Reply>> {
        return Observable.defer {
            val replyList = databaseManager.getReplyWithSearch(search, fromFavorite)
            when {
                sharedPrefManager.getReplySort() == context.getString(R.string.alphabetical_order) ->
                    replyList.sortedBy { it.name }
                sharedPrefManager.getReplySort() == context.getString(R.string.movie_order) ->
                    replyList.sortedBy { it.timestamp }
                sharedPrefManager.getReplySort() == context.getString(R.string.random_order) ->
                    replyList.shuffled()
            }
            Observable.just(replyList)
        }
    }

    override fun initAllReply(): Completable {
        return Completable.defer {
            if (databaseManager.getAllReply().isEmpty()) {
                val replyList = fileManager.buildReplyListFromResource()
                databaseManager.saveReplyList(replyList)
            }
            Completable.complete()
        }
    }

    override fun updateFavoriteReply(replyId: Int, addToFavorite: Boolean): Observable<Reply> {
        return Observable.defer { Observable.just(databaseManager.updateFavoriteReply(replyId, addToFavorite)) }
    }

    override fun getAllReply(fromFavorite: Boolean): Observable<List<Reply>> {
        return Observable.defer {
            val replyList = databaseManager.getAllReply(fromFavorite)
            when {
                sharedPrefManager.getReplySort() == context.getString(R.string.alphabetical_order) ->
                    replyList.sortedBy { it.name }
                sharedPrefManager.getReplySort() == context.getString(R.string.movie_order) ->
                    replyList.sortedBy { it.timestamp }
                sharedPrefManager.getReplySort() == context.getString(R.string.random_order) ->
                    replyList.shuffled()
            }
            Observable.just(replyList)
        }
    }

    override fun updateMultiListenParameter(multiListen: Boolean): Completable {
        sharedPrefManager.setMultiListenEnabled(multiListen)
        return Completable.complete()
    }

    override fun multiListenEnabled(): Observable<Boolean> {
        return Observable.fromCallable { sharedPrefManager.isMultiListenEnabled() }
    }

    override fun getMostListenedReply(): Observable<Reply> {
        return Observable.fromCallable { databaseManager.getMostListenedReply() }
    }

    override fun incrementReplyListenCount(replyId: Int): Completable {
        return Completable.defer {
            databaseManager.incrementReplyListenCount(replyId)
            Completable.complete()
        }
    }

    override fun increaseTotalReplyTime(duration: Long) {
        sharedPrefManager.increaseTotalReplyTime(duration)
    }

    override fun getTotalReplyTime(): Observable<Long> {
        return Observable.fromCallable { sharedPrefManager.getTotalReplyTime() }
    }

    override fun retrieveRandomReplyIdToListen(): Observable<Int> {
        return Observable.defer {
            val replyList = databaseManager.getAllReply()
            val randomNumber = ThreadLocalRandom.current().nextInt(0, replyList.size + 1)
            val replyId = replyList[randomNumber].id
            databaseManager.incrementReplyListenCount(replyId)
            Observable.just(replyId)
        }
    }

    override fun updateReplySort(replySort: String): Completable {
        return Completable.defer {
            sharedPrefManager.setReplySort(replySort)
            Completable.complete()
        }
    }

    override fun getReplySort(): Observable<String> {
        return Observable.fromCallable { sharedPrefManager.getReplySort() }
    }
}