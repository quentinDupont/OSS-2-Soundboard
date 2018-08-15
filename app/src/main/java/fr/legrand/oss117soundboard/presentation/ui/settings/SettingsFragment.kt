package fr.legrand.oss117soundboard.presentation.ui.settings

import android.os.Bundle
import android.view.View
import fr.legrand.oss117soundboard.R
import fr.legrand.oss117soundboard.presentation.extensions.observeSafe
import fr.legrand.oss117soundboard.presentation.ui.base.BaseVMFragment

/**
 * Created by Benjamin on 17/10/2017.
 */

class SettingsFragment : BaseVMFragment<SettingsViewModel>() {
    override fun getLayoutId() = R.layout.fragment_settings

    override val viewModelClass = SettingsViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mostListenedReply.observeSafe(this){

        }
        viewModel.replySort.observeSafe(this){

        }
        viewModel.totalReplyTime.observeSafe(this){

        }
        viewModel.viewState.observeSafe(this){
            if (it.mostListenedReplyAvailable){
//                most_listened.summary = getString(R.string.no_listened_reply)
            }
        }
    }

//    fun updateSwitch(checked: Boolean) {
//        multiListenPreference!!.isChecked = checked
//    }
//
//    fun updateMostListenedReply(replyViewData: ReplyViewData?) {
//        if (replyViewData != null) {
//            mostListenedReplyPreference!!.summary = replyViewData.getMostListenedText()
//        } else {
//
//        }
//    }
//
//    fun updateReplySort(replySort: String) {
//        replySortPreference!!.summary = replySort
//    }
//
//    fun updateTotalReplyTime(hours: Long, minutes: Long, seconds: Long) {
//        totalReplyTimePreference!!.summary = getString(R.string.total_reply_time_text, hours, minutes, seconds)
//    }
//
//    private fun initializePreferences() {
//        multiListenPreference = this.getPreferenceManager().findPreference(getString(R.string.multi_listen_preference_key))
//        replySortPreference = this.getPreferenceManager().findPreference(getString(R.string.reply_sort_preference_key))
//        mostListenedReplyPreference = this.getPreferenceManager().findPreference(getString(R.string.most_listened_reply_preference_key))
//        totalReplyTimePreference = this.getPreferenceManager().findPreference(getString(R.string.total_reply_time_preference_key))
//        randomReplyPreference = this.getPreferenceManager().findPreference(getString(R.string.random_reply_preference_key))
//
//        multiListenPreference!!.setOnPreferenceChangeListener { preference, newValue ->
//            viewModel.updateMultiListenParameter(newValue as Boolean)
//            true
//        }
//
//        randomReplyPreference!!.setOnPreferenceClickListener { preference ->
//            viewModel.listenToRandomReply()
//            true
//        }
//        replySortPreference!!.setOnPreferenceChangeListener { preference, newValue ->
//            viewModel.updateReplySort(newValue as String)
//            replySortPreference!!.summary = newValue
//            true
//        }
//    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
