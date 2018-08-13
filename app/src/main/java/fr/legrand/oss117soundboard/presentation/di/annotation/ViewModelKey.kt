package fr.legrand.oss117soundboard.presentation.di.annotation

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Annotation to identify a ViewModel between all referenced ViewModels in the dagger BindingMap
 *
 * @param value The class of the ViewModel itself
 * @author dfournier
 */
@MapKey
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)