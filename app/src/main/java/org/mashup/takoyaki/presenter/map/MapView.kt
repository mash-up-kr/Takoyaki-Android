package org.mashup.takoyaki.presenter.map

import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.View

interface MapView : View {

    fun resultFoodTrucks(foodTrucks: FoodTrucks)
}
