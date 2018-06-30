package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_truck_detail.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.FoodTruckDetail
import org.mashup.takoyaki.presenter.truckdetail.TruckDetailPresenter
import org.mashup.takoyaki.presenter.truckdetail.TruckDetailView
import javax.inject.Inject

class TruckDetailActivity : DaggerAppCompatActivity(), TruckDetailView {

    companion object {
        private const val TRUCK_NAME = "TRUCK_NAME"

        fun start(context: Context, truckName: String) {
            val intent = Intent(context, TruckDetailActivity::class.java)
            intent.putExtra(TRUCK_NAME, truckName)
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var presenter: TruckDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_detail)

        val truckName = intent.getStringExtra(TRUCK_NAME)
                ?: throw RuntimeException("휘파람 휘파람 휘파라마라바라마팜")
        presenter.attachView(this)
        presenter.getTruckDetail(truckName)
    }

    override fun resultFoodTruckDetail(it: FoodTruckDetail) {
        text_truck_name.text = it.truckName
        text_truck_description.text = it.description
    }

    override fun showErrorMessage(throwable: Throwable) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}
