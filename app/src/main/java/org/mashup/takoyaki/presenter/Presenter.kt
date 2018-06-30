package org.mashup.takoyaki.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<V : View> {

    protected val disposable: CompositeDisposable

    protected var view: V? = null

    init {
        this.disposable = CompositeDisposable()
    }

    fun attachView(view: V) {
        this.view = view
    }

    protected fun checkViewIsNull() {
        if (view == null) {
            throw IllegalArgumentException("View is null")
        }
    }

    fun detachView() {
        disposable.dispose()

        view = null
    }

}
