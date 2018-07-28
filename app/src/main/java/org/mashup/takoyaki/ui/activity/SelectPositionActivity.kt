package org.mashup.takoyaki.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_select_position.*
import org.mashup.takoyaki.R
import android.location.Geocoder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

class SelectPositionActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    companion object {
        const val RETURN_CURRENT_LOCATION = "RETURN_CURRENT_LOCATION"

        fun start(context: Context) {
            context.startActivity(Intent(context, SelectPositionActivity::class.java))
        }
    }

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_position)

        val fragmentMap =
                supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        fragmentMap.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.uiSettings.isTiltGesturesEnabled = false
        googleMap.uiSettings.isCompassEnabled = false
        googleMap.uiSettings.isZoomGesturesEnabled = false
        googleMap.uiSettings.isRotateGesturesEnabled = false

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.566692, 126.978416), 7f))

        googleMap.setOnCameraIdleListener(this)

        btSubmit.setOnClickListener {
            val intent = Intent()
            intent.putExtra(RETURN_CURRENT_LOCATION, tvLocation.text)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCameraIdle() {
        val latLng = googleMap.cameraPosition.target
        getLocationAddress(latLng.latitude, latLng.longitude)
    }

    private fun getLocationAddress(latitude: Double, longitude: Double) {
        val geoCoder = Geocoder(this, Locale.getDefault())
        var currentLocation = ""
        try {
            val address = geoCoder.getFromLocation(latitude, longitude, 1)
            currentLocation = address[0].getAddressLine(0)

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

        tvLocation.text = currentLocation
    }
}

