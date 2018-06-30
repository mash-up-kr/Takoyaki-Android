package org.mashup.takoyaki.data.repository

import io.reactivex.Single
import org.mashup.takoyaki.data.remote.api.ApiService
import org.mashup.takoyaki.data.remote.model.FoodTruckDetail
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-07-01.
 */
class MapRepository @Inject constructor(apiService: ApiService) : BaseRepository(apiService) {

    fun getFoodTrucks(): Single<FoodTrucks> {
        return apiService.getFoodTrucks()
    }

    fun getFoodTruckDetail(truckName: String): Single<FoodTruckDetail> {
        return apiService.getFoodTruckDetail(truckName)
    }
}