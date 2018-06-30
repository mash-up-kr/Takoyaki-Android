package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.mashup.takoyaki.R
import javax.inject.Inject
import com.google.android.gms.maps.model.MarkerOptions
import org.mashup.takoyaki.data.remote.model.FoodTruck
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.turck.TruckPresenter
import org.mashup.takoyaki.presenter.turck.TruckView
import org.mashup.takoyaki.ui.activity.TruckDetailActivity

/**
 * Created by jonghunlee on 2018-06-30.
 */
class TruckFragment @Inject constructor() : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, TruckView {

    companion object {
        private val TAG = TruckFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: TruckPresenter

    private lateinit var googleMap: GoogleMap

    private val foodTrucks = mutableListOf<FoodTruck>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager?.beginTransaction()?.replace(R.id.layout_map, mapFragment)?.commit()
        mapFragment.getMapAsync(this)
        presenter.attachView(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        // 서울시청
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.566692, 126.978416), 7f))
        googleMap.setOnMarkerClickListener(this)
        presenter.getFoodTrucks()
    }

    override fun resultFoodTrucks(foodTrucks: FoodTrucks) {
        this.foodTrucks.clear()
        this.foodTrucks.addAll(foodTrucks.data)

        foodTrucks.data.forEach {
            val marker = googleMap.addMarker(MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude)))
            marker.tag = it.truckName
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.d(TAG, "market tag : ${marker.tag as String}")
        foodTrucks.forEach {
            if (it.truckName == marker.tag as String) {
                TruckDetailActivity.start(activity!!, it.truckName)
                return true
            }
        }

        return true
    }

    override fun showErrorMessage(throwable: Throwable) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}