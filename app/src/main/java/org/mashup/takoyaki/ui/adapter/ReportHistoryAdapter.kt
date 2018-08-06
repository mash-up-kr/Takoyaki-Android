package org.mashup.takoyaki.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.util.DailyHeader
import org.mashup.takoyaki.util.toFormattedString
import java.util.*

class ReportHistoryAdapter(
        private val onClickContent: (ReportHistory) -> Unit)
    : RecyclerView.Adapter<ReportHistoryContentViewHolder>(), StickyHeaderAdapter<ReportHistoryHeaderViewHolder> {

    private val data = mutableListOf<ReportHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportHistoryContentViewHolder =
        ReportHistoryContentViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_report_history_content, parent, false)
        ).apply {
            itemView.setOnClickListener {
                onClickContent(data[adapterPosition])
            }
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ReportHistoryContentViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getHeaderId(position: Int): Long =
        when {
            data.isEmpty() -> StickyHeaderDecoration.NO_HEADER_ID
            data.size == position -> StickyHeaderDecoration.NO_HEADER_ID
            else -> DailyHeader(data[position].uploadDate).id
        }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): ReportHistoryHeaderViewHolder =
        ReportHistoryHeaderViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_report_history_header, parent, false)
        )

    override fun onBindHeaderViewHolder(viewholder: ReportHistoryHeaderViewHolder, position: Int) {
        if (data.size != position)
            viewholder.bind(data[position].uploadDate)
    }

    fun setData(reportHistories: List<ReportHistory>) {
        val testList: List<ReportHistory> = listOf(
                ReportHistory(Date(), "미미타코야끼", "서울시 강서구",
                        "매운맛", 3000, Date()),
                ReportHistory(Date(), "미미타코야끼", "서울시 강서구",
                        "매운맛", 3000, Date()),
                ReportHistory(Date(), "미미타코야끼", "서울시 강서구",
                        "매운맛", 3000, Date())
        )
        data.clear()
        data.addAll(testList)
        notifyDataSetChanged()
    }

}

class ReportHistoryHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateTextView by lazy { view.findViewById<TextView>(R.id.date) }

    fun bind(date: Date) {
        dateTextView.text =
                date.toFormattedString("yyy.MM.dd EEE")
    }
}

class ReportHistoryContentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val truckName: TextView = view.findViewById(R.id.tvTruckName)
    private val location: TextView = view.findViewById(R.id.tvLocation)
    private val menuName: TextView = view.findViewById(R.id.tvMenu)
    private val price: TextView = view.findViewById(R.id.tvPrice)
    private val finishDate: TextView = view.findViewById(R.id.tvFinishDate)

    fun bind(reportHistory: ReportHistory) {
        with(itemView) {
            truckName.text = reportHistory.truckName
            location.text = reportHistory.location
            menuName.text = reportHistory.menuName
            price.text = reportHistory.price.toString()
            finishDate.text = reportHistory.finishDate.toFormattedString("yyy.MM.dd EEE")
        }
    }
}