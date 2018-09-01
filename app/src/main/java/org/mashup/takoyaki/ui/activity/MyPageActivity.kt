package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_my_page.*
import org.mashup.takoyaki.R

class MyPageActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MyPageActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val toolbar: Toolbar = toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        tvBookmark.setOnClickListener { BookmarkActivity.start(this) }
        tvReportHistory.setOnClickListener { ReportHistoryActivity.start(this) }
        tvSetting.setOnClickListener { SettingActivity.start(this) }
    }
}

