package fr.legrand.oss117soundboard.presentation.adapter;


import android.app.Fragment;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.legrand.oss117soundboard.R;
import fr.legrand.oss117soundboard.presentation.di.PerActivity;
import fr.legrand.oss117soundboard.presentation.listener.OnSearchListener;
import fr.legrand.oss117soundboard.presentation.ui.activity.BaseActivity;
import fr.legrand.oss117soundboard.presentation.ui.fragment.BaseFragment;
import fr.legrand.oss117soundboard.presentation.ui.fragment.ReplyListFragment;

/**
 * Created by Benjamin on 12/10/2017.
 */
@PerActivity
public class ReplyPagerAdapter extends FragmentStatePagerAdapter {
    private static final int FRAGMENT_COUNT = 2;

    private static final int FAVORITE_LIST_POSITION = 0;
    private static final int REPLY_LIST_POSITION = 1;

    private List<String> pagerTitles;
    private List<OnSearchListener> searchListenerList;

    @Inject
    public ReplyPagerAdapter(Context context, BaseActivity activity) {
        super(activity.getFragmentManager());
        searchListenerList = new ArrayList<>();
        pagerTitles = new ArrayList<>();
        pagerTitles.add(FAVORITE_LIST_POSITION, context.getString(R.string.favorite_list_title));
        pagerTitles.add(REPLY_LIST_POSITION, context.getString(R.string.reply_list_title));
    }

    @Override
    public Fragment getItem(int position) {
        //No need to handle removal from list for now because all fragments are instantiated at the beginning
        switch (position) {
            case FAVORITE_LIST_POSITION:
                BaseFragment favoriteFragment = ReplyListFragment.newInstance(true);
                if (favoriteFragment instanceof OnSearchListener) {
                    searchListenerList.add((OnSearchListener) favoriteFragment);
                }
                return favoriteFragment;
            case REPLY_LIST_POSITION:
                BaseFragment replyFragment = ReplyListFragment.newInstance(false);
                if (replyFragment instanceof OnSearchListener) {
                    searchListenerList.add((OnSearchListener) replyFragment);
                }
                return replyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitles.get(position);
    }

    public void onSearch(String search) {
        for (OnSearchListener onSearchListener : searchListenerList) {
            onSearchListener.onSearch(search);
        }
    }
}
