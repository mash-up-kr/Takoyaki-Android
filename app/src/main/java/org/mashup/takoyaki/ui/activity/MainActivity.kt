package org.mashup.takoyaki.ui.activity

import android.content.Intent
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
import org.mashup.takoyaki.ui.fragment.TruckFragment


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

        btWriteReview.setOnClickListener { WriteReviewActivity.start(this) }
        btReport.setOnClickListener { ReportActivity.start(this) }
        btSearch.setOnClickListener { SearchActivity.start(this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fragmentHolder.getFragment(FragmentType.MAIN)
                ?.onActivityResult(requestCode, resultCode, intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        //TODO Show My Page Activity
            R.id.menu_my_page -> MyPageActivity.start(this)
        }

        return super.onOptionsItemSelected(item)
    }
}