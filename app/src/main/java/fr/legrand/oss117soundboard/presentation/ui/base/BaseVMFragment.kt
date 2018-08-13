package fr.legrand.oss117soundboard.presentation.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import fr.legrand.oss117soundboard.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * @author dfournier
 */
abstract class BaseVMFragment<T : ViewModel> : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: T

    abstract val viewModelClass: KClass<T>

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass.java)
    }

}