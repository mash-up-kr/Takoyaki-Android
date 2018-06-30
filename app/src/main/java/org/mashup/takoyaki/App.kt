package org.mashup.takoyaki

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.mashup.takoyaki.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
