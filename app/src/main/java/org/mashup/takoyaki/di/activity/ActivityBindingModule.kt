package org.mashup.takoyaki.di.activity

import org.mashup.takoyaki.ui.activity.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.mashup.takoyaki.di.fragment.FragmentScoped
import org.mashup.takoyaki.ui.activity.TruckDetailActivity
import org.mashup.takoyaki.ui.fragment.CloseTruckFragment
import org.mashup.takoyaki.ui.fragment.TruckFragment

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TruckDetailModule::class])
    internal abstract fun truckDetailActivity(): TruckDetailActivity

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun mapFragment(): TruckFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun closeFoodTruckFragment(): CloseTruckFragment
}
