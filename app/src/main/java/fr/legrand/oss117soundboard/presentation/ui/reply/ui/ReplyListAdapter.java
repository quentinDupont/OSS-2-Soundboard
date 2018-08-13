package fr.legrand.oss117soundboard.presentation.ui.reply.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.reply.ui.ReplyListViewHolder;
import fr.legrand.oss117soundboard.presentation.ui.reply.item.ReplyViewData;

/**
 * Created by Benjamin on 30/09/2017.
 */
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListViewHolder> {

    private List<ReplyViewData> replyList;
    private BaseActivity activity;

    private ReplyListViewHolder.OnReplyListenListener onReplyListenListener;

    @Inject
    public ReplyListAdapter(BaseActivity activity) {
        this.activity = activity;
        this.replyList = new ArrayList<>();
    }

    public void setOnReplyListenListener(ReplyListViewHolder.OnReplyListenListener onReplyListenListener) {
        this.onReplyListenListener = onReplyListenListener;
    }

    @Override
    public ReplyListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View resultView = LayoutInflater.from(activity).inflate(R.layout.reply_view_holder, parent, false);
        return new ReplyListViewHolder(resultView, activity);
    }

    @Override
    public void onBindViewHolder(ReplyListViewHolder holder, int position) {
        holder.bindReply(replyList.get(position));
        holder.bindListenListener(onReplyListenListener);
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    public void setItems(List<ReplyViewData> replyList) {
        this.replyList.clear();
        this.replyList.addAll(replyList);
        notifyDataSetChanged();
    }

    public ReplyViewData getItem(int position) {
        return replyList.get(position);
    }

    public void updateItem(ReplyViewData replyViewData, boolean favoriteLayout) {
        int index = replyList.indexOf(replyViewData);
        //Reply removed from favorites and favorite layout is displayed OR reply added to favorites and favorite layout is not displayed
        if (!replyViewData.getReply().isFavorite() && favoriteLayout || replyViewData.getReply().isFavorite() && !favoriteLayout) {
            replyList.remove(index);
            notifyItemRemoved(index);
        } else {
            replyList.add(replyViewData);
            notifyItemInserted(replyList.size());
        }
    }
}
