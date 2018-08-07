package org.mashup.takoyaki.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.Bookmark

class BookmarkAdapter(private val onClickContent: (Bookmark) -> Unit)
    : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    private val data = mutableListOf<Bookmark>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], onClickContent)
    }

    fun setData(bookmarks: List<Bookmark>) {
        val testList: List<Bookmark> = listOf(
            Bookmark("미미타코야끼", "서울시 강서구", true),
            Bookmark("미미타코야끼", "서울시 강서구", true),
            Bookmark("미미타코야끼", "서울시 강서구", true)
        )
        data.clear()
        data.addAll(testList)
        notifyDataSetChanged()
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val truckName: TextView = view.findViewById(R.id.tvTruckName)
        private val truckLocation: TextView = view.findViewById(R.id.tvTruckLocation)

        fun bind(bookmark: Bookmark, onClickContent: (Bookmark) -> Unit) {
            with(itemView) {
                truckName.text = bookmark.truckName
                truckLocation.text = bookmark.location

                setOnClickListener { onClickContent(bookmark) }
            }
        }
    }

}
