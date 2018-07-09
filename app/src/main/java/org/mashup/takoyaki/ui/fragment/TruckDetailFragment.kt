package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_truck_detail.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.FoodTruckDetail
import org.mashup.takoyaki.presenter.truckdetail.TruckDetailPresenter
import org.mashup.takoyaki.presenter.truckdetail.TruckDetailView
import javax.inject.Inject


/**
 * Created by jonghunlee on 2018-07-01.
 */
class TruckDetailFragment : BaseFragment(), TruckDetailView {
    companion object {
        val TAG = TruckDetailFragment::class.java.simpleName

        private const val TRUCK_NAME = "TRUCK_NAME"

        fun newInstance(truckName: String): TruckDetailFragment {
            val fragment = TruckDetailFragment()
            val bundle = Bundle()
            bundle.putString(TRUCK_NAME, truckName)
            fragment.arguments = bundle

            return fragment
        }
    }

    @Inject
    lateinit var presenter: TruckDetailPresenter

    override fun getLayoutId(): Int {
        return R.layout.fragment_truck_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val truckName = arguments?.getString(TRUCK_NAME) ?: throw RuntimeException("Don't cry")
        presenter.attachView(this)
        presenter.getTruckDetail(truckName)
    }

    override fun resultFoodTruckDetail(it: FoodTruckDetail) {
        tvTruckName.text = it.truckName
        tvTruckDescription.text = it.description
    }

    override fun showErrorMessage(throwable: Throwable) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }
}