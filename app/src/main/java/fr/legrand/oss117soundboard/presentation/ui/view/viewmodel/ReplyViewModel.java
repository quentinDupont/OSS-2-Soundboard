package fr.legrand.oss117soundboard.presentation.ui.view.viewmodel;

import fr.legrand.oss117soundboard.data.entity.Reply;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class ReplyViewModel {

    private Reply reply;

    public ReplyViewModel(Reply reply) {
        this.reply = reply;
    }

    public Reply getReply() {
        return reply;
    }

    public String getFormattedDescription(){
        return String.format("%1$s%2$s%3$s", "\"", reply.getDescription(), "\"");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ReplyViewModel && ((ReplyViewModel) obj).getReply().equals(this.getReply());
    }
}
