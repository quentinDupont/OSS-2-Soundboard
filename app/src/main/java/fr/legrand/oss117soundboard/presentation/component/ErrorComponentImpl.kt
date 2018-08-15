package fr.legrand.oss117soundboard.presentation.component

import android.content.Context
import android.support.design.widget.Snackbar
import android.widget.Toast

import javax.inject.Inject

import fr.legrand.oss117soundboard.R
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity

/**
 * Created by Benjamin on 17/10/2017.
 */

class ErrorComponentImpl @Inject constructor(
        private val context: Context,
        private val baseActivity: BaseActivity
) : ErrorComponent {

    override fun displayListenErrorToast() {
        Toast.makeText(context, R.string.listen_error, Toast.LENGTH_LONG).show()
    }

    override fun displayListenErrorSnackbar() {
        Snackbar.make(baseActivity.getRootView(), R.string.listen_error, Snackbar.LENGTH_LONG)
                .show()
    }

}
