package org.mashup.takoyaki.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.mashup.takoyaki.R
import javax.inject.Inject
import com.google.android.gms.maps.model.MarkerOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_truck.*
import org.mashup.takoyaki.data.remote.model.FoodTruck
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.turck.TruckPresenter
import org.mashup.takoyaki.presenter.turck.TruckView
import org.mashup.takoyaki.ui.adapter.TruckPagerAdapter
import org.mashup.takoyaki.util.SharedPreferencesUtil
import android.content.IntentSender
import android.support.v4.view.ViewPager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.*


/**
 * Created by jonghunlee on 2018-06-30.
 */
class TruckFragment @Inject constructor() : BaseFragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnMarkerClickListener, TruckView {

    companion object {
        private val TAG = TruckFragment::class.java.simpleName

        private const val REQUEST_CHECK_LOCATION_SETTING = 2001

        private const val MIN_MAP_ZOOM_LEVEL = 15f
        private const val DEFAULT_LATITUDE = 37.566692
        private const val DEFAULT_LONGITUDE = 126.978416
    }

    @Inject
    lateinit var preferencesUtil: SharedPreferencesUtil

    @Inject
    lateinit var presenter: TruckPresenter

    private lateinit var googleMap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private lateinit var truckPagerAdapter: TruckPagerAdapter

    private val foodTrucks = mutableListOf<FoodTruck>()
    private val markers = mutableListOf<Marker>()

    private var isMyPosition = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_truck
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        locationRequest = LocationRequest()
        setHighAccuracyLocationRequest()

        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager?.beginTransaction()?.replace(R.id.mapLayout, mapFragment)?.commit()

        mapLayout.visibility = View.INVISIBLE

        vpTrucks.clipToPadding = false
        vpTrucks.setPadding(64, 0, 64, 0)
        vpTrucks.pageMargin = 64
        vpTrucks.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.d(TAG, "onPageScrolled#position : $position")
                val truck = truckPagerAdapter.getItem(position)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(truck.latitude,
                                                                          truck.longitude)))
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        truckPagerAdapter = TruckPagerAdapter()
        vpTrucks.adapter = truckPagerAdapter

        rgDistanceFilter.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbOneHandM -> Log.d(TAG, "100M")
                R.id.rbThreeHandM -> Log.d(TAG, "300M")
                R.id.rbFiveHandM -> Log.d(TAG, "500M")
                R.id.rbOneKm -> Log.d(TAG, "1KM")
            }
        }

        btMyPosition.setOnClickListener {
            if (btSearchThisPosition.visibility == View.VISIBLE) {
                btSearchThisPosition.visibility = View.GONE
            }

            setHighAccuracyLocationRequest()

            getCurrentLocation()
        }

        btSearchThisPosition.setOnClickListener {
            btSearchThisPosition.visibility = View.GONE
            val centerPosition = googleMap.cameraPosition
            Log.d(TAG, "Camera position latitude : ${centerPosition.target.latitude}, " +
                    "longitude : ${centerPosition.target.longitude}")
        }

        presenter.attachView(this)
        mapFragment.getMapAsync(this)
    }

    private fun setHighAccuracyLocationRequest() {
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isCompassEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        googleMap.setMinZoomPreference(MIN_MAP_ZOOM_LEVEL)

        googleMap.setOnCameraIdleListener(this)
        googleMap.setOnMarkerClickListener(this)

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                         Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe({
                               if (it) {
                                   // getLastLocation()
                                   getCurrentLocation()
                               } else {
                                   Toast.makeText(activity,
                                                  R.string.permission_location_request_denied,
                                                  Toast.LENGTH_SHORT).show()
                                   activity?.finish()
                               }
                           }, {

                           })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationClient.lastLocation?.addOnSuccessListener {
            if (it != null) {
                showCurrentLocation(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val settingClient = LocationServices.getSettingsClient(activity!!)
        settingClient.checkLocationSettings(LocationSettingsRequest
                                                    .Builder()
                                                    .addLocationRequest(locationRequest!!)
                                                    .setAlwaysShow(true)
                                                    .build())
                .addOnSuccessListener(activity!!) {
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                                                               locationCallback,
                                                               Looper.getMainLooper())
                }
                .addOnFailureListener {
                    failLocationSetting(it)
                }
    }

    private fun showCurrentLocation(location: Location) {
        isMyPosition = true
        Log.d(TAG,
              "showCurrentLocation#latitude : ${location.latitude}, longitude : ${location.longitude}")
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude,
                                                                  location.longitude)))
        mapLayout.visibility = View.VISIBLE
        fusedLocationClient.removeLocationUpdates(locationCallback)

        presenter.getFoodTrucks()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            showCurrentLocation(locationResult.lastLocation)
        }
    }

    private fun failLocationSetting(e: Exception) {
        val statusCode = (e as ApiException).statusCode

        when (statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                val resolvableApiException = e as ResolvableApiException
                resolvableApiException.startResolutionForResult(activity,
                                                                REQUEST_CHECK_LOCATION_SETTING)
            } catch (sie: IntentSender.SendIntentException) {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_LOCATION_SETTING -> {
                if (resultCode != Activity.RESULT_OK) {
                    Log.e(TAG, "User cancel location provide cancel")
                    when (locationRequest.priority) {
                        LocationRequest.PRIORITY_HIGH_ACCURACY ->
                            locationRequest.priority =
                                    LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                        LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY ->
                            locationRequest.priority =
                                    LocationRequest.PRIORITY_NO_POWER
                    }
                }

                getCurrentLocation()
            }
        }
    }

    override fun resultFoodTrucks(foodTrucks: FoodTrucks) {
        this.foodTrucks.clear()
        this.foodTrucks.addAll(foodTrucks.data)

        truckPagerAdapter.add(foodTrucks.data)
        vpTrucks.currentItem = 0

        markers.forEach { it.remove() }
        markers.clear()

        foodTrucks.data.forEach {
            val marker = googleMap.addMarker(MarkerOptions()
                                                     .position(LatLng(it.latitude, it.longitude)))
            marker.tag = it.truckName

            markers.add(marker)
        }
    }

    override fun onCameraIdle() {
        if (isMyPosition) {
            isMyPosition = false
            return
        }

        Log.d(TAG, "onCameraIdle")

        if (btSearchThisPosition.visibility == View.GONE) {
            btSearchThisPosition.visibility = View.VISIBLE
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d(TAG, "market tag : ${marker.tag as String}")

        return true
    }

    override fun showErrorMessage(throwable: Throwable) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}