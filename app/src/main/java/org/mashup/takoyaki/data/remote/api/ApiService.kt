package org.mashup.takoyaki.data.remote.api

import io.reactivex.Single
import org.mashup.takoyaki.data.remote.model.FoodTruckDetail
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("map/positions")
    fun getFoodTrucks(): Single<FoodTrucks>

    @GET("map/detail")
    fun getFoodTruckDetail(@Query("truckName") truckName: String): Single<FoodTruckDetail>


}
