package fr.legrand.oss117soundboard.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.view.viewholder.ReplySearchResultViewHolder;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class ReplySearchAdapter extends RecyclerView.Adapter<ReplySearchResultViewHolder> {

    private List<ReplyViewModel> replyList;
    private Context context;

    @Inject
    public ReplySearchAdapter(Context context) {
        this.context = context;
        this.replyList = new ArrayList<>();
    }

    @Override
    public ReplySearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View resultView = LayoutInflater.from(context).inflate(R.layout.reply_search_result_view_holder, parent, false);
        return new ReplySearchResultViewHolder(resultView);
    }

    @Override
    public void onBindViewHolder(ReplySearchResultViewHolder holder, int position) {
        holder.bindReply(replyList.get(position));
    }

    @Override
    public int getItemCount() {
        return replyList.size();
    }

    public void setItems(List<ReplyViewModel> replyList){
        this.replyList.clear();
        this.replyList.addAll(replyList);
        notifyDataSetChanged();
    }
}
