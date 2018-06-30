package org.mashup.takoyaki.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.SupportMapFragment
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import org.mashup.takoyaki.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}