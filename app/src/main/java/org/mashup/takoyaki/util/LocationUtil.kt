package org.mashup.takoyaki.util

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.support.v4.app.FragmentActivity
import com.google.android.gms.location.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.mashup.takoyaki.util.exception.PermissionDeniedException


/**
 * Created by jonghunlee on 2018-08-01.
 */

object LocationUtil {

    private var locationCallback: LocationCallback? = null

    fun getLastLocation(activity: FragmentActivity): Observable<Location> {
        return checkLocationPermission(activity).flatMap { isGrant ->
            Observable.create<Location> { source ->
                if (isGrant) {
                    LocationServices.getFusedLocationProviderClient(activity)
                            .lastLocation.addOnSuccessListener {
                        source.onNext(it)
                    }.addOnFailureListener {
                        source.onError(it)
                    }
                } else {
                    source.onError(PermissionDeniedException())
                }
            }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
        }
    }

    fun getUpdateLocation(activity: FragmentActivity, locationRequest: LocationRequest): Observable<Location> {
        return checkLocationPermission(activity).flatMap { isGrant ->
            Observable.create<Location> { source ->
                if (isGrant) {
                    getUpdateLocation(activity, source, locationRequest)
                } else {
                    source.onError(PermissionDeniedException())
                }
            }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUpdateLocation(activity: FragmentActivity, source: ObservableEmitter<Location>, locationRequest: LocationRequest) {
        LocationServices.getSettingsClient(activity)
                .checkLocationSettings(getLocationSettingRequest(locationRequest))
                .addOnSuccessListener {
                    val fusedLocationClient =
                            LocationServices.getFusedLocationProviderClient(activity)
                    initLocationCallback(fusedLocationClient, source)
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                                                               locationCallback,
                                                               Looper.getMainLooper())
                }
                .addOnFailureListener {
                    source.onError(it)
                }
    }

    private fun getLocationSettingRequest(locationRequest: LocationRequest): LocationSettingsRequest {
        return LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()
    }

    private fun initLocationCallback(fusedLocationClient: FusedLocationProviderClient, source: ObservableEmitter<Location>) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                source.onNext(locationResult.lastLocation)
                fusedLocationClient.removeLocationUpdates(locationCallback)
                locationCallback = null
            }
        }
    }

    private fun checkLocationPermission(activity: FragmentActivity): Observable<Boolean> {
        return RxPermissions(activity).request(Manifest.permission.ACCESS_FINE_LOCATION,
                                               Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}

