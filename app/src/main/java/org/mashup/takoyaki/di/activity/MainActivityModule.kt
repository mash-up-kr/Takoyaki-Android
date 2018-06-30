package org.mashup.takoyaki.di.activity


import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager

import org.mashup.takoyaki.di.fragment.FragmentScoped
import org.mashup.takoyaki.ui.activity.MainActivity

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
class MainActivityModule {

    @Provides
    @ActivityScoped
    internal fun provideFragmentManager(activity: MainActivity): FragmentManager {
        return activity.supportFragmentManager
    }
}
