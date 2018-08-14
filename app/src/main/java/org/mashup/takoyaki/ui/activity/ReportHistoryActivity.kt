package org.mashup.takoyaki.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration
import kotlinx.android.synthetic.main.activity_report_history.*
import org.jetbrains.anko.startActivityForResult
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.ReportHistory
import org.mashup.takoyaki.presenter.mypage.reporthistory.ReportHistoryPresenter
import org.mashup.takoyaki.presenter.mypage.reporthistory.ReportHistoryView
import org.mashup.takoyaki.ui.adapter.ReportHistoryAdapter
import org.mashup.takoyaki.util.LinearDividerDecoration

class ReportHistoryActivity : AppCompatActivity(), ReportHistoryView {

    val presenter: ReportHistoryPresenter = ReportHistoryPresenter(this)

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ReportHistoryActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_history)

        presenter.onCreate()
    }

    override fun setAdapter() {
        with(recyclerview) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            ReportHistoryAdapter(presenter::onContentClicked).let {
                adapter = it
                addItemDecoration(LinearDividerDecoration(context, showAtLastItem = false))
                addItemDecoration(ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration(it))
            }
        }
    }

    override fun setDataToAdapter(reportHistories: List<ReportHistory>) {
        (recyclerview.adapter as? ReportHistoryAdapter)?.run {
            setData(reportHistories)
        }
        recyclerview.visibility = View.VISIBLE
    }

    override fun moveToHistoryDetailActivity(id: String) {
        startActivityForResult<ReportHistoryDetailActivity>(
            ReportHistoryDetailActivity.REQUEST_REPORT,
            ReportHistoryDetailActivity.KEY_ID to id
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                ReportHistoryDetailActivity.REQUEST_REPORT -> {
                    data?.getStringExtra(ReportHistoryDetailActivity.KEY_ID)?.let {
                        (recyclerview.adapter as? ReportHistoryAdapter)
                    }
                }
            }
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
