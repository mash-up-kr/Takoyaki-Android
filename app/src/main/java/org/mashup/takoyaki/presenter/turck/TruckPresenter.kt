package org.mashup.takoyaki.presenter.turck

import org.mashup.takoyaki.data.repository.FoodTruckRepository
import org.mashup.takoyaki.presenter.Presenter
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-07-01.
 */
class TruckPresenter @Inject
constructor(private val repository: FoodTruckRepository) : Presenter<TruckView>() {

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