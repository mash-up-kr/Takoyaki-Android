package org.mashup.takoyaki.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.Bookmark

class BookmarkAdapter(var items: List<Bookmark>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private val data = arrayListOf<Bookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_bookmark, parent, false)
        ) { /* explicitly empty */ }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) { is ViewHolder -> holder.bind(items[position]) }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val truckName: TextView = view.findViewById(R.id.tvTruckName)
        private val truckLocation: TextView = view.findViewById(R.id.tvTruckLocation)

        fun bind(bookmark: Bookmark) {
            with(itemView) {
                truckName.text = bookmark.truckName
                truckLocation.text = bookmark.location
            }
        }
    }

}
