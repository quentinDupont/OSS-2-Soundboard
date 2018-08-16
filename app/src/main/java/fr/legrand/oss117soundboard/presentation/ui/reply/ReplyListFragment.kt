package fr.legrand.oss117soundboard.presentation.ui.reply

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import fr.legrand.oss117soundboard.R
import fr.legrand.oss117soundboard.presentation.extensions.hide
import fr.legrand.oss117soundboard.presentation.extensions.observeSafe
import fr.legrand.oss117soundboard.presentation.extensions.setToGrayScale
import fr.legrand.oss117soundboard.presentation.extensions.show
import fr.legrand.oss117soundboard.presentation.ui.base.BaseVMFragment
import fr.legrand.oss117soundboard.presentation.ui.main.ReplySharedViewModel
import fr.legrand.oss117soundboard.presentation.ui.reply.ui.ReplyListAdapter
import kotlinx.android.synthetic.main.fragment_reply_list.*
import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

private const val FAVORITE_KEY = "FAVORITE_KEY"

class ReplyListFragment : BaseVMFragment<ReplyListViewModel>() {
    override val viewModelClass = ReplyListViewModel::class
    @Inject
    lateinit var replyListAdapter: ReplyListAdapter

    private lateinit var sharedViewModel: ReplySharedViewModel

    override fun getLayoutId() = R.layout.fragment_reply_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel = ViewModelProviders.of(activity!!, viewModelFactory)[ReplySharedViewModel::class.java]

        viewModel.favorite = arguments?.getBoolean(FAVORITE_KEY) ?: false

        initializeRecyclerView()

        fragment_reply_list_placeholder_image.setToGrayScale()

        sharedViewModel.searchRequested.observeSafe(this) {
            viewModel.updateSearch(it)
            viewModel.getAllReply()
        }
        sharedViewModel.replyUpdated.observeSafe(this) {
            viewModel.getAllReply()
        }

        viewModel.replyUpdated.observeSafe(this) {
            sharedViewModel.onReplyUpdated()
        }

        viewModel.replyListLiveData.observeSafe(this) {
            replyListAdapter.setItems(it)
        }

        viewModel.viewState.observeSafe(this) {
            if (it.displayingPlaceholder) {
                displayPlaceholder()
            } else {
                displayReplyList()
            }
            fragment_reply_list_refresh_layout.isRefreshing = it.refreshing
        }
    }

    private fun displayPlaceholder() {
        fragment_reply_list_recycler.hide()
        fragment_reply_list_placeholder.show()
    }

    private fun displayReplyList() {
        fragment_reply_list_recycler.show()
        fragment_reply_list_placeholder.hide()
    }


    private fun initializeRecyclerView() {
        fragment_reply_list_refresh_layout.setColorSchemeResources(R.color.colorPrimary)
        fragment_reply_list_refresh_layout.setOnRefreshListener {
            viewModel.getAllReply()
        }
        fragment_reply_list_recycler.layoutManager = LinearLayoutManager(activity)
        fragment_reply_list_recycler.adapter = replyListAdapter

        replyListAdapter.onListenClickListener = {
            viewModel.incrementReplyCount(it)
        }
        replyListAdapter.onFavoriteClickListener = { id, favorite ->
            viewModel.updateFavoriteReply(id, favorite)
        }
    }

    companion object {
        fun newInstance(fromFavorite: Boolean): ReplyListFragment {
            val args = Bundle().apply { putBoolean(FAVORITE_KEY, fromFavorite) }
            return ReplyListFragment().apply { arguments = args }
        }
    }


}