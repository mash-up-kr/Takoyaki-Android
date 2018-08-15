package org.mashup.takoyaki.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_select_position.*
import org.mashup.takoyaki.R
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import org.mashup.takoyaki.ui.fragment.TruckFragment
import org.mashup.takoyaki.util.LocationUtil
import org.mashup.takoyaki.util.exception.PermissionDeniedException
import java.io.IOException
import java.util.*

class SelectPositionActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    companion object {
        const val RETURN_CURRENT_LOCATION = "RETURN_CURRENT_LOCATION"

        private const val REQUEST_CHECK_LOCATION_SETTING = 1001

        private val TAG = SelectPositionActivity::class.java.simpleName

        fun start(context: Context) {
            context.startActivity(Intent(context, SelectPositionActivity::class.java))
        }
    }

    private lateinit var googleMap: GoogleMap

    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_position)

        locationRequest = LocationRequest()
        setHighAccuracyLocationRequest()

        val fragmentMap =
                supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        fragmentMap.getMapAsync(this)
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

        googleMap.setOnCameraIdleListener(this)

        btSubmit.setOnClickListener {
            val intent = Intent()
            intent.putExtra(RETURN_CURRENT_LOCATION, tvLocation.text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        LocationUtil.getUpdateLocation(this, locationRequest).subscribe(
                {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude,
                                                                                  it.longitude),
                                                                           17f))
                }, {
                    when (it) {
                        is PermissionDeniedException -> {
                            Toast.makeText(this,
                                           R.string.permission_location_request_denied,
                                           Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        is ApiException -> {
                            failLocationSetting(it)
                        }
                        else -> {
                            Log.e(TAG, "get Location error", it)
                        }
                    }
                })
    }

    private fun failLocationSetting(e: Exception) {
        val statusCode = (e as ApiException).statusCode

        when (statusCode) {
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                val resolvableApiException = e as ResolvableApiException
                resolvableApiException.startResolutionForResult(this,
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

    override fun onCameraIdle() {
        val latLng = googleMap.cameraPosition.target
        getLocationAddress(latLng.latitude, latLng.longitude)
    }

    private fun getLocationAddress(latitude: Double, longitude: Double) {
        val geoCoder = Geocoder(this, Locale.getDefault())
        var address = ""
        try {
            val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
            address = addresses[0].getAddressLine(0)

            //                String s1 = address.get(0).getCountryName();        // 국가명
            //                String s2 = address.get(0).getAdminArea();          // 시
            //                String s3 = address.get(0).getLocality();           // 구 메인, "성남시 중원구" 인 경우 "성남시"가 들어감
            //                String s4 = address.get(0).getSubLocality();        // 구 서브데이터  "성남시 중원구" 인 경우 "중원구"가 들어감
            //                String s5 = address.get(0).getThoroughfare();             // 동
            //                String s6 = address.get(0).getSubThoroughfare());         // 번지
            //                String s7 = address.get(0).getFeatureName()());          // 번지
            //                String s8 = address.get(0).getAddressLine(0).toString(); // 국가명 시 군 구 동 번지
        } catch (e: IOException) {
        }

        tvLocation.text = address
    }
}

