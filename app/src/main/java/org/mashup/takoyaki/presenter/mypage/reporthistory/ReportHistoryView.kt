package org.mashup.takoyaki.presenter.mypage.reporthistory

import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.presenter.View

interface ReportHistoryView : View {
    fun setAdapter()
    fun setDataToAdapter(reportHistories: List<ReportHistory>)
}