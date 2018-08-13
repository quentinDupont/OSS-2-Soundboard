package fr.legrand.oss117soundboard.presentation.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.CallSuper
import fr.legrand.oss117soundboard.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by Benjamin on 19/04/2018.
 */
abstract class BaseVMActivity<T : ViewModel> : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: T

    abstract val viewModelClass: KClass<T>

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass.java)
    }
}