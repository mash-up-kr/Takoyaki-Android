package org.mashup.takoyaki.presenter.mypage.reporthistory

import org.mashup.takoyaki.data.remote.model.ReportHistory

class ReportHistoryPresenter(val view: ReportHistoryView) {

    fun onCreate() {
        view.setAdapter()
    }

    fun onContentClicked(reportHistory: ReportHistory) {

    }

}
