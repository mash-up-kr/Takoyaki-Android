package org.mashup.takoyaki.ui.fragment

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
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
import kotlinx.android.synthetic.main.fragment_map.*
import org.mashup.takoyaki.R
import javax.inject.Inject
import com.google.android.gms.maps.model.MarkerOptions
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.map.MapPresenter
import org.mashup.takoyaki.presenter.map.MapView

/**
 * Created by jonghunlee on 2018-06-30.
 */
class MapFragment @Inject constructor() : Fragment(), OnMapReadyCallback, MapView {

    @Inject
    lateinit var presenter: MapPresenter

    private lateinit var googleMap: GoogleMap

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

        presenter.getFoodTrucks()
    }

    override fun resultFoodTrucks(foodTrucks: FoodTrucks) {
        foodTrucks.data.forEach {
            googleMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
        }
    }

    override fun showErrorMessage(throwable: Throwable) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}