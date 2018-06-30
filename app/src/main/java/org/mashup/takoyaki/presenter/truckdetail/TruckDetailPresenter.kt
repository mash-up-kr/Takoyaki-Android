package org.mashup.takoyaki.presenter.truckdetail

import org.mashup.takoyaki.data.repository.FoodTruckRepository
import org.mashup.takoyaki.presenter.Presenter
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-07-01.
 */
class TruckDetailPresenter @Inject
constructor(private val repository: FoodTruckRepository) : Presenter<TruckDetailView>() {

    fun getTruckDetail(truckName: String) {
        view?.showProgress()

        disposable.add(repository.getFoodTruckDetail(truckName).doFinally {
            view?.hideProgress()
        }.subscribe({
            view?.resultFoodTruckDetail(it)
        }, {
            view?.showErrorMessage(it)
        }))
    }
}