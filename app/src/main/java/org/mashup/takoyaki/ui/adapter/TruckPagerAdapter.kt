package org.mashup.takoyaki.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.FoodTruck


/**
 * Created by jonghunlee on 2018-07-01.
 */
class TruckPagerAdapter : RecycledPagerAdapter<TruckPagerAdapter.TruckViewHolder>() {

    private val foodTrucks = mutableListOf<FoodTruck>()

    override fun onCreateViewHolder(parent: ViewGroup): TruckViewHolder {
        return TruckViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_truck_pager,
                                                                           parent,
                                                                           false))
    }

    override fun onBindViewHolder(viewHolder: TruckViewHolder, position: Int) {
        viewHolder.bind(foodTrucks[position])
    }

    override fun getCount(): Int {
        return foodTrucks.size
    }

    fun add(foodTrucks: List<FoodTruck>) {
        this.foodTrucks.clear()
        this.foodTrucks.addAll(foodTrucks)

        notifyDataSetChanged()
    }

    fun getItem(position: Int): FoodTruck {
        return foodTrucks[position]
    }

    inner class TruckViewHolder(itemView: View) : ViewHolder(itemView) {

        private val textTruckName: TextView = itemView.findViewById(R.id.tvTruckName)

        fun bind(foodTruck: FoodTruck) {
            textTruckName.text = foodTruck.truckName
        }
    }

}