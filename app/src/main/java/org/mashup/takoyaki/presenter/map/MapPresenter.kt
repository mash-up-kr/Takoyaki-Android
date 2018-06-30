package org.mashup.takoyaki.presenter.map

import org.mashup.takoyaki.data.repository.MapRepository
import org.mashup.takoyaki.presenter.Presenter
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-07-01.
 */
class MapPresenter @Inject
constructor(private val repository: MapRepository) : Presenter<MapView>() {

    fun getFoodTrucks() {
        view?.showProgress()
        disposable.add(repository.getFoodTrucks().doFinally {
            view?.hideProgress()
        }.subscribe({
            view?.resultFoodTrucks(it)
        }, {
            view?.showErrorMessage(it)
        }))
    }


}