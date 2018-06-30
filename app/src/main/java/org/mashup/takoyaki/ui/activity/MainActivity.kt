package org.mashup.takoyaki.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.ui.adapter.TabPagerAdapter
import org.mashup.takoyaki.ui.fragment.FragmentHolder
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentHolder: FragmentHolder

    @Inject
    lateinit var tabPagerAdapter: TabPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        view_pager.adapter = tabPagerAdapter
        layout_tab.setupWithViewPager(view_pager)
    }
}