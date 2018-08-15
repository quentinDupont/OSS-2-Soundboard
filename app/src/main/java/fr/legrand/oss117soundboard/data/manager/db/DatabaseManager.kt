package fr.legrand.oss117soundboard.data.manager.db

import com.raizlabs.android.dbflow.annotation.Database
import fr.legrand.oss117soundboard.data.entity.Reply

/**
 * Created by Benjamin on 30/09/2017.
 */
@Database(name = DatabaseManagerImpl.NAME, version = DatabaseManagerImpl.VERSION)
interface DatabaseManager {
    fun getAllReply(): List<Reply>

    fun getMostListenedReply(): Reply

    fun getReplyWithSearch(search: String, fromFavorite: Boolean): List<Reply>
    fun getAllReply(fromFavorite: Boolean): List<Reply>

    fun updateFavoriteReply(replyId: Int, addToFavorite: Boolean): Reply

    fun saveReplyList(replyList: List<Reply>)

    fun incrementReplyListenCount(replyId: Int)


}
