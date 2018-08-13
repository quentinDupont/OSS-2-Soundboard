package fr.legrand.oss117soundboard.presentation.ui.reply;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.presentation.ui.reply.ui.ReplyListAdapter;
import fr.legrand.oss117soundboard.presentation.presenter.ReplyListPresenter;
import fr.legrand.oss117soundboard.presentation.ui.base.BaseFragment;
import fr.legrand.oss117soundboard.presentation.ui.listener.OnSearchListener;
import fr.legrand.oss117soundboard.presentation.ui.reply.ui.ReplyListViewHolder;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.ReplyListView;
import fr.legrand.oss117soundboard.presentation.ui.reply.item.ReplyViewData;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class ReplyListFragment extends BaseFragment implements ReplyListView, ReplyListViewHolder.OnReplyListenListener, OnSearchListener {

    private static final String FAVORITE_KEY = "fr.legrand.oss117soundboard.presentation.ui.reply.ReplyListFragment.FAVORITE_KEY";

    @Inject
    ReplyListPresenter replyListPresenter;
    @Inject
    ReplyListAdapter replyListAdapter;

    @BindView(R.id.fragment_reply_list_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.fragment_reply_list_placeholder)
    RelativeLayout searchPlaceholder;
    @BindView(R.id.fragment_reply_list_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.fragment_reply_list_placeholder_image)
    ImageView searchPlaceholderImage;
    @BindView(R.id.fragment_reply_list_placeholder_text)
    TextView searchPlaceholderText;


    public static ReplyListFragment newInstance(boolean fromFavorite) {
        Bundle args = new Bundle();

        args.putBoolean(FAVORITE_KEY, fromFavorite);

        ReplyListFragment fragment = new ReplyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View homeView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_reply_list, container, false);
        getFragmentComponent().inject(this);
        ButterKnife.bind(this, homeView);

        replyListPresenter.setReplyListView(this);

        setPlaceholderToGrayScale();

        return homeView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        replyListPresenter.getAllReply(getArguments().getBoolean(FAVORITE_KEY));
    }

    @Override
    public void updateSearchReplyList(List<ReplyViewData> replyViewDataList) {
        replyListAdapter.setItems(replyViewDataList);
    }

    @Override
    public void displayPlaceholder() {
        searchRecycler.setVisibility(View.GONE);
        searchPlaceholder.setVisibility(View.VISIBLE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void displayReplyList() {
        searchRecycler.setVisibility(View.VISIBLE);
        searchPlaceholder.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void updateFavoriteReply(ReplyViewData replyViewData) {
        replyListAdapter.updateItem(replyViewData, getArguments().getBoolean(FAVORITE_KEY));
        if (replyListAdapter.getItemCount() == 0) {
            displayPlaceholder();
        } else {
            displayReplyList();
        }
    }

    @Override
    public void onReplyListenClick(int position) {
        replyListPresenter.incrementReplyCount(replyListAdapter.getItem(position).getReply().getId());
    }

    @Override
    public void onReplyFavoriteClick(int position) {
        Reply reply = replyListAdapter.getItem(position).getReply();
        replyListPresenter.updateFavoriteReply(reply.getId(), !reply.isFavorite());
    }

    @Override
    public void onSearch(String search) {
        boolean fromFavorite = getArguments().getBoolean(FAVORITE_KEY);
        if (search == null || search.isEmpty()) {
            replyListPresenter.getAllReply(fromFavorite);
        } else {
            replyListPresenter.getAllReplyWithSearch(search, fromFavorite);
        }
    }

    private void initializeRecyclerView() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> replyListPresenter.requestRefreshData());
        searchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecycler.setAdapter(replyListAdapter);
        replyListAdapter.setOnReplyListenListener(this);
    }

    private void setPlaceholderToGrayScale() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        searchPlaceholderImage.setColorFilter(cf);
        searchPlaceholderImage.setImageAlpha(128);
    }


}
