package org.mashup.takoyaki.presenter.turck

import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.View

interface TruckView : View {

    fun resultFoodTrucks(foodTrucks: FoodTrucks)
}
