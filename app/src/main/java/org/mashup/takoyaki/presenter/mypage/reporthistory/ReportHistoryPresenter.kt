package org.mashup.takoyaki.presenter.mypage.reporthistory

import android.util.Log
import org.mashup.takoyaki.data.remote.model.ReportHistory
import java.util.*

class ReportHistoryPresenter(val view: ReportHistoryView) {
    var garaData: List<ReportHistory> = listOf()

    fun onCreate() {
        addGaraData()
        view.setAdapter()
        view.setDataToAdapter(garaData)
    }

    fun onContentClicked(reportHistory: ReportHistory) {
        Log.e("onContentClicked", reportHistory.id)
        view.moveToHistoryDetailActivity(reportHistory.id)
    }

    private fun addGaraData() {
        garaData = listOf(
            ReportHistory("0", Date(), "미미타코야끼", "서울시 강서구",
                "매운맛", 3000, Date()),
            ReportHistory("1", Date(), "미미타코야끼", "서울시 강서구",
                "매운맛", 3000, Date()),
            ReportHistory("2", Date(), "미미타코야끼", "서울시 강서구",
                "매운맛", 3000, Date())
        )
    }

}
