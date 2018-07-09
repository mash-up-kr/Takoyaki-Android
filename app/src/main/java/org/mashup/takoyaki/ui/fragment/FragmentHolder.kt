package org.mashup.takoyaki.ui.fragment

import android.support.v4.app.Fragment
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-06-30.
 */

enum class FragmentType {
    MAIN
}

class FragmentHolder @Inject
constructor(private val truckFragment: TruckFragment) {

    fun getFragment(type: FragmentType): Fragment? {
        return when (type) {
            FragmentType.MAIN -> truckFragment
        }
    }
}