package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration
import kotlinx.android.synthetic.main.activity_report_history.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.presenter.mypage.reporthistory.ReportHistoryPresenter
import org.mashup.takoyaki.presenter.mypage.reporthistory.ReportHistoryView
import org.mashup.takoyaki.ui.adapter.ReportHistoryAdapter

class ReportHistoryActivity : AppCompatActivity(), ReportHistoryView {
    val presenter: ReportHistoryPresenter = ReportHistoryPresenter(this)
    private var stickyHeaderDecoration: StickyHeaderDecoration? = null

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
            stickyHeaderDecoration?.run {
                removeItemDecoration(this)
            }
            adapter = ReportHistoryAdapter(presenter::onContentClicked)

            stickyHeaderDecoration = ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration(
                    adapter as StickyHeaderAdapter<*>
            )
            addItemDecoration(stickyHeaderDecoration)
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
