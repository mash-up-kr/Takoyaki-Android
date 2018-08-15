package org.mashup.takoyaki.presenter.mypage.reporthistorydetail

import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.presenter.View

interface ReportHistoryDetailView : View {
    fun setAdapter()
    fun setDataToAdapter(reportHistories: List<ReportHistory>)
}