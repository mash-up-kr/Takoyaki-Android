package org.mashup.takoyaki.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<V : View> {

    protected val disposable: CompositeDisposable = CompositeDisposable()

    protected var view: V? = null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView() {
        disposable.dispose()

        view = null
    }

}
