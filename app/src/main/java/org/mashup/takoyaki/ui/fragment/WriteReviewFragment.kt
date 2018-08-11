package org.mashup.takoyaki.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mashup.takoyaki.R


/**
 * Created by jonghunlee on 2018-08-04.
 */
class WriteReviewFragment : Fragment() {

    companion object {
        val TAG = WriteReviewFragment::class.java.simpleName

        fun newInstance(): WriteReviewFragment {
            return WriteReviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_write_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}