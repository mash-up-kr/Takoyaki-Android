package org.mashup.takoyaki.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.mashup.takoyaki.ui.fragment.FragmentHolder
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-06-30.
 */
class TabPagerAdapter @Inject
constructor(fm: FragmentManager,
            private val fragmentHolder: FragmentHolder,
            private val titles: Array<String>) : FragmentPagerAdapter(fm) {

    companion object {
        const val MAX_FRAGMENT_COUNT = 2
        const val FRAGMENT_MAP = 0
        const val FRAGMENT_CLOSE = 1
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            FRAGMENT_MAP -> return fragmentHolder.mapFragment
            FRAGMENT_CLOSE -> return fragmentHolder.closeFoodTruckFragment
        }

        return null
    }

    override fun getCount(): Int {
        return MAX_FRAGMENT_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}