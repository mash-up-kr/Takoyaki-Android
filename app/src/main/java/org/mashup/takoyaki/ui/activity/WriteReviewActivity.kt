package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_write_review.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.ui.fragment.TruckListFragment

class WriteReviewActivity : AppCompatActivity() {

    companion object {
        private val TAG = WriteReviewActivity::class.java.simpleName

        fun start(context: Context) {
            context.startActivity(Intent(context, WriteReviewActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.activity_write_review)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction()
                .replace(R.id.flContent,
                         TruckListFragment.newInstance(),
                         TruckListFragment.TAG).commit()
    }
}
