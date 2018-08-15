package org.mashup.takoyaki.presenter.mypage.reporthistorydetail

import android.util.Log
import org.mashup.takoyaki.data.remote.model.ReportHistory
import java.util.*

class ReportHistoryDetailPresenter(val view: ReportHistoryDetailView, var id: String) {
    var garaData: List<ReportHistory> = listOf()

    fun onCreate() {
        addGaraData()
        view.setAdapter()
        view.setDataToAdapter(garaData)
    }

    fun onContentClicked(reportHistory: ReportHistory) {
        Log.e("#", "onContentClicked")
    }

    private fun addGaraData() {
        garaData = listOf(
            ReportHistory("0", Date(), "미미타코야끼", "서울시 강서구",
                "매운맛", 3000, Date()))

        id = garaData.indexOf(garaData[0]).toString()
    }
}