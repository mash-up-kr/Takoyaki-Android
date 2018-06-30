package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

/**
 * Created by jonghunlee on 2018-06-30.
 */
class MapFragment @Inject constructor() : SupportMapFragment(), OnMapReadyCallback {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 서울시청
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.566692, 126.978416), 14f))
    }
}