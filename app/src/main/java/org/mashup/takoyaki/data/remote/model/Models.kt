package org.mashup.takoyaki.data.remote.model


/**
 * Created by jonghunlee on 2018-07-01.
 */


data class FoodTrucks(val data: List<FoodTruck>)

data class FoodTruck(val truckName: String,
                     val latitude: Double,
                     val longitude: Double,
                     val region: String)

data class FoodTruckDetail(val truckName: String,
                           val description: String,
                           val type: String)