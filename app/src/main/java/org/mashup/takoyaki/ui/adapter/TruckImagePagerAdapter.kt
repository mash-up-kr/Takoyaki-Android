package org.mashup.takoyaki.ui.adapter

import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_truck_image_pager.view.*
import kotlinx.android.synthetic.main.item_truck_pager.view.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.FoodTruck

class TruckImagePagerAdapter : RecycledPagerAdapter<TruckImagePagerAdapter.TruckImageViewHolder>() {

    companion object {
        private const val BUTTON_COUNT = 1
    }

    private val truckImageUrls = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup): TruckImageViewHolder {
        return TruckImageViewHolder(LayoutInflater.from(parent.context)
                                            .inflate(R.layout.item_truck_image_pager,
                                                     parent,
                                                     false))
    }

    override fun onBindViewHolder(viewHolder: TruckImageViewHolder, position: Int) {
        (viewHolder.itemView as ImageView).setImageURI(truckImageUrls[position])
    }

    override fun getCount(): Int {
        return truckImageUrls.size
    }

    fun add(urls: List<Uri>) {
        for (i in urls.count() - 1 downTo 0) {
            truckImageUrls.add(0, urls[i])
        }

        notifyDataSetChanged()
    }

    inner class TruckImageViewHolder(itemView: View) : ViewHolder(itemView)

}
