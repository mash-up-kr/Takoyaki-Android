package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_truck.*
import org.mashup.takoyaki.R


/**
 * Created by jonghunlee on 2018-08-04.
 */
class TruckListFragment : Fragment() {

    companion object {
        val TAG = TruckListFragment::class.java.simpleName

        fun newInstance(): TruckListFragment {
            return TruckListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_truck, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        svTruck.isIconified = false
        svTruck.setIconifiedByDefault(false)

        fragmentManager?.beginTransaction()
                ?.add(R.id.flContent, WriteReviewFragment.newInstance(), WriteReviewFragment.TAG)
                ?.addToBackStack(WriteReviewFragment.TAG)
                ?.commit()
    }
}