package org.mashup.takoyaki.presenter


interface View {

    fun showErrorMessage(throwable: Throwable)

    fun showProgress()

    fun hideProgress()
}
