package fr.legrand.oss117soundboard.presentation

import android.app.Activity
import android.app.Application

import com.raizlabs.android.dbflow.config.FlowManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import javax.inject.Inject

/**
 * Created by Benjamin on 30/09/2017.
 */

class OSSApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this).build().inject(this)
        FlowManager.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
