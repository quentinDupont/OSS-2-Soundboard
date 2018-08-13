package fr.legrand.oss117soundboard.presentation.di.module.activity

import dagger.Binds
import dagger.Module
import fr.legrand.oss117soundboard.presentation.di.annotation.PerActivity
import fr.legrand.oss117soundboard.presentation.ui.base.BaseActivity
import fr.legrand.oss117soundboard.presentation.ui.main.MainActivity

@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {
    @PerActivity
    @Binds
    abstract fun bindBaseActivity(mainActivity: MainActivity): BaseActivity

//    @PerFragment
//    @ContributesAndroidInjector
//    abstract fun rankingFragmentInjector(): LeaderboardFragment

}