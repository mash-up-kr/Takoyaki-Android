package org.mashup.takoyaki.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.util.toFormattedString

class ReportHistoryDetailAdapter : RecyclerView.Adapter<ReportHistoryContentDetailViewHolder>() {

    private val data = mutableListOf<ReportHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHistoryContentDetailViewHolder =
        ReportHistoryContentDetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_report_history_detail, parent, false)
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ReportHistoryContentDetailViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(reportHistories: List<ReportHistory>) {
        data.clear()
        data.addAll(reportHistories)
        notifyDataSetChanged()
    }

}

class ReportHistoryContentDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val truckName by lazy { view.findViewById<TextView>(R.id.tvTruckName) }
    private val location by lazy { view.findViewById<TextView>(R.id.tvLocation) }
    private val menuName by lazy { view.findViewById<TextView>(R.id.tvMenu) }
    private val price by lazy { view.findViewById<TextView>(R.id.tvPrice) }
    private val finishDate by lazy { view.findViewById<TextView>(R.id.tvFinishDate) }

    fun bind(reportHistory: ReportHistory) {
        with(itemView) {
            truckName?.text = reportHistory.truckName
            location?.text = reportHistory.location
            menuName?.text = reportHistory.menuName
            price?.text = reportHistory.price.toString()
            finishDate?.text = reportHistory.finishDate.toFormattedString("yyy.MM.dd EEE")
        }
    }
}