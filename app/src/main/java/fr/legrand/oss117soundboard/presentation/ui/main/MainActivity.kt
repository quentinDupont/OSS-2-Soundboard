package fr.legrand.oss117soundboard.presentation.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.media.AudioManager
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.SearchView
import fr.legrand.oss117soundboard.R
import fr.legrand.oss117soundboard.presentation.extensions.hide
import fr.legrand.oss117soundboard.presentation.extensions.observeSafe
import fr.legrand.oss117soundboard.presentation.extensions.show
import fr.legrand.oss117soundboard.presentation.navigator.MainNavigator
import fr.legrand.oss117soundboard.presentation.ui.base.BaseVMActivity
import fr.legrand.oss117soundboard.presentation.ui.main.ui.ReplyPagerAdapter
import fr.legrand.oss117soundboard.presentation.utils.StringUtils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */


private const val OFFSCREEN_PAGE_LOADED_NUMBER = 3

class MainActivity : BaseVMActivity<MainViewModel>() {

    override val viewModelClass = MainViewModel::class

    @Inject
    lateinit var mainNavigator: MainNavigator
    @Inject
    lateinit var replyPagerAdapter: ReplyPagerAdapter

    private lateinit var replySharedViewModel: ReplySharedViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //We change the media volume affected by hardware buttons
        volumeControlStream = AudioManager.STREAM_MUSIC

        setContentView(R.layout.activity_main)

        replySharedViewModel = ViewModelProviders.of(this, viewModelFactory)[ReplySharedViewModel::class.java]

        viewModel.replyLoaded.observeSafe(this) {
            loading_progress_bar.hide()
            reply_view_pager.show()
            main_tab_layout.show()
            initViewPager()
        }

        activity_main_search_button.setOnClickListener {
            initializeSearchView()
            updateToolbarLayout(false)
        }

        activity_main_toolbar_stop_listen.setOnClickListener {
            viewModel.releaseRunningPlayers()
        }

        activity_main_toolbar_search_back.setOnClickListener {
            activity_main_toolbar_search_view.setOnQueryTextListener(null)
            activity_main_toolbar_search_view.clearFocus()
            activity_main_toolbar_search_view.isIconified = true
            replySharedViewModel.searchRequested.postValue(ReplySharedViewModel.NO_SEARCH)
            updateToolbarLayout(true)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.releaseRunningPlayers()
    }


    override fun getLayoutId() = R.layout.activity_main
    override fun getRootView(): View = main_activity_root_layout

    private fun initViewPager(){
        reply_view_pager.adapter = replyPagerAdapter
        reply_view_pager.offscreenPageLimit = OFFSCREEN_PAGE_LOADED_NUMBER
        main_tab_layout.setupWithViewPager(reply_view_pager)
        reply_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                replyPagerAdapter.setCurrentPosition(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun updateToolbarLayout(displayBaseLayout: Boolean) {
        if (displayBaseLayout) {
            activity_main_toolbar_layout_base.show()
            activity_main_toolbar_layout_search.hide()
        } else {
            activity_main_toolbar_layout_base.hide()
            activity_main_toolbar_layout_search.show()
        }
    }

    private fun initializeSearchView() {
        activity_main_toolbar_search_view.isIconified = false
        activity_main_toolbar_search_view.requestFocusFromTouch()
        activity_main_toolbar_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                activity_main_toolbar_search_view.clearFocus()
                if (s.matches(StringUtils.SEARCH_EASTER_EGG_REGEX.toRegex())) {
                    mainNavigator.launchBrowsingApp()
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                replySharedViewModel.searchRequested.postValue(s)
                return true
            }
        })
    }
}
