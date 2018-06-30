package org.mashup.takoyaki.di.activity

import org.mashup.takoyaki.ui.activity.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity
}
