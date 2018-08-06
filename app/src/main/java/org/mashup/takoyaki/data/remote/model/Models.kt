package org.mashup.takoyaki.data.remote.model

import java.util.*


/**
 * Created by jonghunlee on 2018-07-01.
 */


data class FoodTrucks(val data: List<FoodTruck>)

data class FoodTruck(val id: Int,
                     val truckName: String,
                     val latitude: Double,
                     val longitude: Double,
                     val region: String)

data class FoodTruckDetail(val truckName: String,
                           val description: String,
                           val type: String)

data class Bookmark(val truckName: String,
                    val location: String,
                    val isBookmarked: Boolean)

data class ReportHistory(val uploadDate: Date,
                         val truckName: String,
                         val location: String,
                         val menuName: String,
                         val price: Long,
                         val finishDate: Date)
