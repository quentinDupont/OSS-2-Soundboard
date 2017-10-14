package fr.legrand.oss117soundboard.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.di.PerFragment;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.view.viewholder.ReplyListViewHolder;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */
@PerFragment
public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListViewHolder> {

    private List<ReplyViewModel> replyList;
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

    public void setItems(List<ReplyViewModel> replyList) {
        this.replyList.clear();
        this.replyList.addAll(replyList);
        notifyDataSetChanged();
    }

    public ReplyViewModel getItem(int position) {
        return replyList.get(position);
    }

    public void updateItem(ReplyViewModel replyViewModel, boolean favoriteLayout) {
        int index = replyList.indexOf(replyViewModel);
        //Reply removed from favorites and favorite layout is displayed OR reply added to favorites and favorite layout is not displayed
        if (!replyViewModel.getReply().isFavorite() && favoriteLayout || replyViewModel.getReply().isFavorite() && !favoriteLayout) {
            replyList.remove(index);
            notifyItemRemoved(index);
        } else {
            replyList.add(replyViewModel);
            notifyItemInserted(replyList.size());
        }
    }
}
