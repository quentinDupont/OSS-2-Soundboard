package fr.legrand.oss117soundboard.presentation.ui.reply.item

import fr.legrand.oss117soundboard.data.entity.Reply

/**
 * Created by Benjamin on 30/09/2017.
 */

data class ReplyViewData(private val reply: Reply) {

    var isExpanded: Boolean = false

    val formattedDescription = String.format("%s%s%s", "\"", reply.description, "\"")

    val mostListenedText = String.format("%1s (%2d)", reply.name, reply.listenCount)
}
