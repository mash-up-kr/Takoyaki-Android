package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.mashup.takoyaki.R
import javax.inject.Inject
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_truck.*
import org.mashup.takoyaki.data.remote.model.FoodTruck
import org.mashup.takoyaki.data.remote.model.FoodTrucks
import org.mashup.takoyaki.presenter.turck.TruckPresenter
import org.mashup.takoyaki.presenter.turck.TruckView
import org.mashup.takoyaki.ui.adapter.TruckPagerAdapter

/**
 * Created by jonghunlee on 2018-06-30.
 */
class TruckFragment @Inject constructor() : BaseFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, TruckView {

    companion object {
        private val TAG = TruckFragment::class.java.simpleName
    }

    @Inject
    lateinit var presenter: TruckPresenter

    private lateinit var googleMap: GoogleMap

    private val foodTrucks = mutableListOf<FoodTruck>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_truck
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager?.beginTransaction()?.replace(R.id.mapLayout, mapFragment)?.commit()
        mapFragment.getMapAsync(this)
        presenter.attachView(this)

        viewPager.clipToPadding = false
        viewPager.setPadding(64, 0, 64, 0)
        viewPager.pageMargin = 64
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

        val adapter = TruckPagerAdapter()

        viewPager.adapter = adapter
        adapter.add(foodTrucks.data)

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
                fragmentManager?.beginTransaction()
                        ?.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
                        ?.add(R.id.flContents, TruckDetailFragment.newInstance(it.truckName))
                        ?.addToBackStack(TruckDetailFragment.TAG)?.commit()
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