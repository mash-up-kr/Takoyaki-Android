package org.mashup.takoyaki.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBarDrawerToggle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.ui.fragment.FragmentHolder
import javax.inject.Inject
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import org.mashup.takoyaki.ui.fragment.FragmentType


class MainActivity : DaggerAppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    @Inject
    lateinit var fragmentHolder: FragmentHolder

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.setToolbarNavigationClickListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                onBackPressed()
            }
        }

        supportFragmentManager.addOnBackStackChangedListener(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContents,
                         fragmentHolder.getFragment(FragmentType.MAIN)).commit()
    }

    override fun onBackStackChanged() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount == 0) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toggle.isDrawerIndicatorEnabled = true
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            arrowCloseAnimation()
        } else {
            arrowOpenAnimation()
//            toggle.isDrawerIndicatorEnabled = false
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        toggle.syncState()
    }

    private fun arrowOpenAnimation() {
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.addUpdateListener {
            val slideOffSet = it.animatedValue as Float
            toggle.drawerArrowDrawable.progress = slideOffSet
        }

        anim.interpolator = DecelerateInterpolator()
        anim.duration = 300
        anim.start()
    }

    private fun arrowCloseAnimation() {
        val anim = ValueAnimator.ofFloat(1f, 0f)
        anim.addUpdateListener {
            val slideOffSet = it.animatedValue as Float
            toggle.drawerArrowDrawable.progress = slideOffSet
        }

        anim.interpolator = DecelerateInterpolator()
        anim.duration = 300
        anim.start()
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount == 0) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}