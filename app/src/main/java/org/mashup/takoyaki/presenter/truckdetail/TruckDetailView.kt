package org.mashup.takoyaki.presenter.truckdetail

import org.mashup.takoyaki.data.remote.model.FoodTruckDetail
import org.mashup.takoyaki.presenter.View


/**
 * Created by jonghunlee on 2018-07-01.
 */
interface TruckDetailView : View {
    fun resultFoodTruckDetail(it: FoodTruckDetail)
}