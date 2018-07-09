package org.mashup.takoyaki.di.activity

import org.mashup.takoyaki.ui.activity.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.mashup.takoyaki.di.fragment.FragmentScoped
import org.mashup.takoyaki.ui.fragment.TruckDetailFragment
import org.mashup.takoyaki.ui.fragment.TruckFragment

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun truckFragment(): TruckFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun truckDetailFragment(): TruckDetailFragment
}
