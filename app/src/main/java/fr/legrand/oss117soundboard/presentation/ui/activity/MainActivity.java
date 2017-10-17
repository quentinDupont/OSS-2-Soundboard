package fr.legrand.oss117soundboard.presentation.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.adapter.ReplyPagerAdapter;
import fr.legrand.oss117soundboard.presentation.navigator.MainNavigator;
import fr.legrand.oss117soundboard.presentation.navigator.listener.MainNavigatorListener;
import fr.legrand.oss117soundboard.presentation.presenter.MainPresenter;
import fr.legrand.oss117soundboard.presentation.ui.view.viewinterface.MainView;

/**
 * Created by Benjamin on 30/09/2017.
 */

public class MainActivity extends BaseActivity implements MainView, MainNavigatorListener {

    @Inject
    MainNavigator mainNavigator;
    @Inject
    MainPresenter mainPresenter;
    @Inject
    ReplyPagerAdapter replyPagerAdapter;

    @BindView(R.id.main_tab_layout)
    TabLayout mainTabLayout;

    @BindView(R.id.reply_view_pager)
    ViewPager replyViewPager;

    @BindView(R.id.main_activity_root_layout)
    RelativeLayout rootLayout;
    @BindView(R.id.activity_main_toolbar_layout_base)
    RelativeLayout toolbarBaseLayout;
    @BindView(R.id.activity_main_toolbar_layout_search)
    RelativeLayout toolbarSearchLayout;
    @BindView(R.id.activity_main_toolbar_search_view)
    SearchView searchView;
    @BindView(R.id.activity_main_toolbar_search_back)
    ImageView searchBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        mainPresenter.setMainView(this);

        mainPresenter.initAllReply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.onStop();
    }

    @Override
    public void requestDisplayReplyListFragment() {
        replyViewPager.setAdapter(replyPagerAdapter);
        mainTabLayout.setupWithViewPager(replyViewPager);
        replyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                replyPagerAdapter.setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.activity_main_search_button)
    public void searchButtonClicked() {
        initializeSearchView();
        updateToolbarLayout(false);
    }

    @OnClick(R.id.activity_main_toolbar_search_back)
    public void searchBackClicked() {
        searchView.setOnQueryTextListener(null);
        searchView.setIconified(true);
        searchView.clearFocus();
        replyPagerAdapter.onSearch(null, true);
        updateToolbarLayout(true);
    }

    @Override
    public void updateAllLayout() {
        replyPagerAdapter.onSearch(searchView.getQuery().toString(), false);
    }

    @Override
    public View getRootView() {
        return rootLayout;
    }

    private void updateToolbarLayout(boolean displayBaseLayout) {
        toolbarBaseLayout.setVisibility(displayBaseLayout ? View.VISIBLE : View.GONE);
        toolbarSearchLayout.setVisibility(displayBaseLayout ? View.GONE : View.VISIBLE);
    }

    private void initializeSearchView() {
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(getString(R.string.easter_egg_search))) {
                    mainNavigator.launchBrowsingApp();
                } else {
                    replyPagerAdapter.onSearch(s, true);
                }
                return true;
            }
        });
    }
}
