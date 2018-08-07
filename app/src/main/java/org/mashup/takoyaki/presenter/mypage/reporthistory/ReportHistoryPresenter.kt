package org.mashup.takoyaki.presenter.mypage.reporthistory

import android.util.Log
import org.mashup.takoyaki.data.remote.model.ReportHistory

class ReportHistoryPresenter(val view: ReportHistoryView) {
    var garaData: List<ReportHistory> = listOf()

    fun onCreate() {
        view.setAdapter()
        view.setDataToAdapter(garaData)
    }

    fun onContentClicked(reportHistory: ReportHistory) {
        Log.e("#", "onContentClicked")
    }

}
