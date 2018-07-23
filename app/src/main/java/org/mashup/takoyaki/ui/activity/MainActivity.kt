package org.mashup.takoyaki.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.ui.fragment.FragmentHolder
import javax.inject.Inject
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import org.mashup.takoyaki.ui.fragment.FragmentType


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var fragmentHolder: FragmentHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragmentHolder.getFragment(FragmentType.MAIN))
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        //TODO Show My Page Activity
            R.id.menu_my_page -> Log.d("test", "test")
        }

        return super.onOptionsItemSelected(item)
    }
}