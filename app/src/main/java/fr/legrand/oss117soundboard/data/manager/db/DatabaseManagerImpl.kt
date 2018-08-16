package fr.legrand.oss117soundboard.data.manager.db

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.Method
import com.raizlabs.android.dbflow.sql.language.OperatorGroup
import com.raizlabs.android.dbflow.sql.language.SQLite
import fr.legrand.oss117soundboard.data.entity.Reply
import fr.legrand.oss117soundboard.data.entity.Reply_Table
import fr.legrand.oss117soundboard.data.exception.NoListenedReplyException
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

class DatabaseManagerImpl @Inject
constructor() : DatabaseManager {
    companion object {
        const val NAME = "OSSDatabase"
        const val VERSION = 1
    }

    override fun getReplyWithSearch(search: String, fromFavorite: Boolean): List<Reply> {
        val searchOperator = OperatorGroup.clause().and(Reply_Table.description.like(String.format("%s%s%s", "%", search, "%")))
                .or(Reply_Table.name.like(String.format("%s%s%s", "%", search, "%")))
        return SQLite.select().from(Reply::class.java)
                .where(searchOperator)
                .and(Reply_Table.isFavorite.`is`(fromFavorite))
                .orderBy(Reply_Table.name, true)
                .queryList()
    }

    override fun getAllReply(fromFavorite: Boolean): List<Reply> {
        return SQLite.select().from(Reply::class.java)
                .where(Reply_Table.isFavorite.`is`(fromFavorite))
                .orderBy(Reply_Table.name, true)
                .queryList()
    }

    override fun getAllReply(): List<Reply> = SQLite.select().from(Reply::class.java).orderBy(Reply_Table.name, true).queryList()

    override fun updateFavoriteReply(replyId: Int, addToFavorite: Boolean): Reply {
        val reply = SQLite.select().from(Reply::class.java).where(Reply_Table.id.eq(replyId)).querySingle()
        if (reply != null) {
            reply.isFavorite = addToFavorite
            reply.update()
        } else {
            throw RuntimeException()
        }
        return reply
    }

    override fun saveReplyList(replyList: List<Reply>) {
        FlowManager.getDatabase(DatabaseManager::class.java).executeTransaction {
            replyList.forEach { it.save() }
        }
    }

    override fun getMostListenedReply(): Reply {
        val cursor = SQLite.select(Method.max(Reply_Table.listenCount).`as`("max")).from(Reply::class.java).query()
        if (cursor != null && cursor.moveToFirst()) {
            val max = cursor.getInt(0)
            cursor.close()
            val reply = SQLite.select().from(Reply::class.java).where(Reply_Table.listenCount.eq(max)).querySingle()
            return if (reply == null || reply.listenCount == 0) {
                throw NoListenedReplyException()
            } else {
                reply
            }
        } else {
            throw NoListenedReplyException()
        }
    }

    override fun incrementReplyListenCount(replyId: Int) {
        val reply = SQLite.select().from(Reply::class.java).where(Reply_Table.id.eq(replyId)).querySingle()
        if (reply != null) {
            reply.listenCount = reply.listenCount + 1
            reply.save()
        }
    }
}
