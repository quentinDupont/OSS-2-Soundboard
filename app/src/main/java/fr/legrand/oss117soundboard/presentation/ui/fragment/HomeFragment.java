package fr.legrand.oss117soundboard.presentation.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.data.entity.Reply;
import fr.legrand.oss117soundboard.presentation.adapter.ReplySearchAdapter;
import fr.legrand.oss117soundboard.presentation.presenter.HomePresenter;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.HomeView;
import fr.legrand.oss117soundboard.presentation.ui.view.viewmodel.ReplyViewModel;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class HomeFragment extends Fragment implements HomeView {

    @BindView(R.id.fragment_home_toolbar_layout_base)
    RelativeLayout toolbarBaseLayout;
    @BindView(R.id.fragment_home_toolbar_layout_search)
    RelativeLayout toolbarSearchLayout;
    @BindView(R.id.fragment_home_toolbar_search_view)
    SearchView searchView;

    @BindView(R.id.fragment_home_sound_layout)
    RelativeLayout homeSoundLayout;

    @BindView(R.id.fragment_home_search_recycler)
    RecyclerView searchRecycler;

    @Inject
    HomePresenter homePresenter;

    @Inject
    ReplySearchAdapter replySearchAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View homeView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, container, false);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, homeView);
        return homeView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        initializeSearchView();
    }

    @OnClick(R.id.fragment_home_search_button)
    public void searchButtonClicked() {
        updateToolbarLayout(false);
    }


    private void initializeRecyclerView() {
        searchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecycler.setAdapter(replySearchAdapter);
    }

    private void initializeSearchView() {
        searchView.setOnCloseListener(() -> {
            updateToolbarLayout(true);
            updateMainLayout(true);
            return true;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //TODO temporary
                createViewModelList();
                updateMainLayout(false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void updateToolbarLayout(boolean displayBaseLayout) {
        toolbarBaseLayout.setVisibility(displayBaseLayout ? View.VISIBLE : View.GONE);
        toolbarSearchLayout.setVisibility(displayBaseLayout ? View.GONE : View.VISIBLE);
    }

    private void updateMainLayout(boolean displaySoundLayout) {
        homeSoundLayout.setVisibility(displaySoundLayout ? View.VISIBLE : View.GONE);
        searchRecycler.setVisibility(displaySoundLayout ? View.GONE : View.VISIBLE);
    }


    private void createViewModelList() {
        List<ReplyViewModel> replyViewModels = new ArrayList<>();
        Reply r = new Reply();
        r.setName("FIRST RESULT");
        r.setDescription("FIRST RESULT DESCRIPTION");
        replyViewModels.add(new ReplyViewModel(r));
        r = new Reply();
        r.setName("SECOND RESULT");
        r.setDescription("SECOND RESULT DESCRIPTION");
        replyViewModels.add(new ReplyViewModel(r));
        r = new Reply();
        r.setName("THIRD RESULT");
        r.setDescription("THIRD RESULT DESCRIPTION");
        replyViewModels.add(new ReplyViewModel(r));
        replySearchAdapter.setItems(replyViewModels);
    }

}
