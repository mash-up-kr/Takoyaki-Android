package org.mashup.takoyaki.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_report.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.ui.adapter.TruckImagePagerAdapter
import org.mashup.takoyaki.util.image.Glide4Engine

class ReportActivity : DaggerAppCompatActivity() {

    companion object {
        private val TAG = ReportActivity::class.java.simpleName

        private const val REQUEST_IMAGE_SELECT = 1001
        private const val REQUEST_LOCATION_SELECT = 1002

        private const val MAX_IMAGE_COUNT = 3

        fun start(context: Context) {
            context.startActivity(Intent(context, ReportActivity::class.java))
        }
    }

    private lateinit var imagePagerAdapter: TruckImagePagerAdapter
    private lateinit var rxPermission: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        imagePagerAdapter = TruckImagePagerAdapter()
        truckImagePager.adapter = imagePagerAdapter

        rxPermission = RxPermissions(this)

        btAddImage.setOnClickListener { addTruckImage() }
        btSelectPosition.setOnClickListener {
            startActivityForResult(Intent(this, SelectPositionActivity::class.java),
                                   REQUEST_LOCATION_SELECT)
        }

       spinnerExpireTime.adapter = ArrayAdapter<String>(this,
                                                         android.R.layout.simple_spinner_dropdown_item,
                                                         resources.getStringArray(R.array.report_expire_times))

        btSubmit.setOnClickListener {
            if (checkInputTruckReport()) {
                //TODO Send Report data
            }
        }
    }

    private fun checkInputTruckReport(): Boolean {
        return true
    }

    private fun addTruckImage() {
        rxPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({
                               if (it) {
                                   Matisse.from(this).choose(MimeType.ofImage())
                                           .countable(true)
                                           .capture(true)
                                           .captureStrategy(
                                                   CaptureStrategy(true,
                                                                   "org.mashup.takoyaki.fileprovider"))
                                           .maxSelectable(MAX_IMAGE_COUNT - imagePagerAdapter.count)
                                           .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                           .thumbnailScale(0.85f).imageEngine(Glide4Engine())
                                           .forResult(REQUEST_IMAGE_SELECT)
                               } else {
                                   Toast.makeText(this,
                                                  getString(R.string.permission_request_denied),
                                                  Toast.LENGTH_SHORT).show()
                               }
                           }, {

                           })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_IMAGE_SELECT) {
            val urls = Matisse.obtainResult(data)
            imagePagerAdapter.add(urls)
            if (imagePagerAdapter.count == MAX_IMAGE_COUNT) {
                btAddImage.visibility = View.GONE
            }
        }

        if (requestCode == REQUEST_LOCATION_SELECT) {
            val location = data.getStringExtra(SelectPositionActivity.RETURN_CURRENT_LOCATION)
            if (!TextUtils.isEmpty(location)) {
                btSelectPosition.text = location
            }
        }
    }
}
