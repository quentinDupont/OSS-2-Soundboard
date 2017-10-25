package fr.legrand.oss117soundboard.presentation.ui.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class ReplyListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.reply_search_result_view_holder_name)
    TextView replyName;
    @BindView(R.id.reply_view_holder_description)
    TextView replyDescription;
    @BindView(R.id.reply_view_holder_listen_button)
    Button listenButton;
    @BindView(R.id.reply_view_holder_favorite_button)
    Button favoriteButton;
    @BindView(R.id.reply_view_holder_description_toggle)
    ImageView descriptionToggle;
    @BindView(R.id.reply_view_holder_title_area)
    LinearLayout titleArea;

    private Context context;

    public ReplyListViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindReply(ReplyViewModel replyViewModel) {
        replyName.setText(replyViewModel.getReply().getName());
        replyDescription.setText(replyViewModel.getFormattedDescription());
        if (replyViewModel.getReply().isFavorite()) {
            favoriteButton.setText(context.getString(R.string.delete));
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_remove), null, null, null);
        } else {
            favoriteButton.setText(context.getString(R.string.favorite));
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_add), null, null, null);
        }
        if (replyViewModel.isExpanded()) {
            replyDescription.setVisibility(View.VISIBLE);
            descriptionToggle.setImageResource(R.drawable.ic_toggle_up);
        } else {
            replyDescription.setVisibility(View.GONE);
            descriptionToggle.setImageResource(R.drawable.ic_toggle_down);
        }
        titleArea.setOnClickListener(view -> {
            if (replyDescription.getVisibility() == View.VISIBLE) {
                replyViewModel.setExpanded(false);
                replyDescription.setVisibility(View.GONE);
                descriptionToggle.setImageResource(R.drawable.ic_toggle_down);
            } else {
                replyViewModel.setExpanded(true);
                replyDescription.setVisibility(View.VISIBLE);
                descriptionToggle.setImageResource(R.drawable.ic_toggle_up);
            }
        });
    }

    public void bindListenListener(OnReplyListenListener onReplyListenListener) {
        if (onReplyListenListener != null) {
            listenButton.setOnClickListener(view -> onReplyListenListener.onReplyListenClick(getAdapterPosition()));
            favoriteButton.setOnClickListener(view -> onReplyListenListener.onReplyFavoriteClick(getAdapterPosition()));
        }
    }

    public interface OnReplyListenListener {
        void onReplyListenClick(int position);

        void onReplyFavoriteClick(int position);
    }
}
