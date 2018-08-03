package org.mashup.takoyaki.di.activity


import android.support.v4.app.FragmentManager
import org.mashup.takoyaki.ui.activity.MainActivity
import dagger.Module
import dagger.Provides
import org.mashup.takoyaki.R


@Module
class MainActivityModule {

    @Provides
    @ActivityScoped
    internal fun provideFragmentManager(activity: MainActivity): FragmentManager {
        return activity.supportFragmentManager
    }
}
