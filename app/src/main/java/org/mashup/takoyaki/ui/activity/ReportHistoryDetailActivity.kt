package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_report_history.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.presenter.mypage.reporthistorydetail.ReportHistoryDetailPresenter
import org.mashup.takoyaki.presenter.mypage.reporthistorydetail.ReportHistoryDetailView
import org.mashup.takoyaki.ui.adapter.ReportHistoryDetailAdapter

class ReportHistoryDetailActivity : AppCompatActivity(), ReportHistoryDetailView {
    val presenter: ReportHistoryDetailPresenter by lazy { ReportHistoryDetailPresenter(this, intent.getStringExtra(KEY_ID)) }

    companion object {
        const val REQUEST_REPORT = 0
        const val KEY_ID = "report_id"

        fun start(context: Context) {
            context.startActivity(Intent(context, ReportHistoryDetailActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_history_detail)

        presenter.onCreate()
    }

    override fun setAdapter() {
        with(recyclerview) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            ReportHistoryDetailAdapter().let {
                adapter = it
            }
        }
    }

    override fun setDataToAdapter(reportHistories: List<ReportHistory>) {
        (recyclerview.adapter as? ReportHistoryDetailAdapter)?.run {
            setData(reportHistories)
        }
        recyclerview.visibility = View.VISIBLE
    }

    override fun showErrorMessage(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
