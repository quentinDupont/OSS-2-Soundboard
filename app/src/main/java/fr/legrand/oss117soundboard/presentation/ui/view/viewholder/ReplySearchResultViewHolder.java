package fr.legrand.oss117soundboard.presentation.ui.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class ReplySearchResultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reply_search_result_view_holder_name)
    TextView replyName;

    @BindView(R.id.reply_search_result_view_holder_description)
    TextView replyDescription;

    public ReplySearchResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindReply(ReplyViewModel replyViewModel) {
        replyName.setText(replyViewModel.getReply().getName());
        replyDescription.setText(replyViewModel.getFormattedDescription());
    }
}
