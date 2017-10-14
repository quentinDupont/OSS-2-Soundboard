package fr.legrand.oss117soundboard.presentation.ui.view.viewholder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

    @BindView(R.id.reply_search_result_view_holder_description)
    TextView replyDescription;


    @BindView(R.id.reply_search_result_view_holder_listen_button)
    Button listenButton;

    @BindView(R.id.reply_search_result_view_holder_favorite_button)
    Button favoriteButton;

    private Context context;

    public ReplyListViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void bindReply(ReplyViewModel replyViewModel) {
        replyName.setText(replyViewModel.getReply().getName());
        replyDescription.setText(replyViewModel.getFormattedDescription());
        if(replyViewModel.getReply().isFavorite()){
            favoriteButton.setTextColor(ContextCompat.getColor(context, R.color.favorite_on_button_color));
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_on, 0, 0, 0);
            favoriteButton.setText(context.getString(R.string.delete));
        }else{
            favoriteButton.setTextColor(ContextCompat.getColor(context, R.color.favorite_off_button_color));
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_off, 0, 0, 0);
            favoriteButton.setText(context.getString(R.string.favorite));
        }
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
