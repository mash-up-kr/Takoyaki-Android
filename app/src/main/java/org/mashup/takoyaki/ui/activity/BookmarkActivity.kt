package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bookmark.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.Bookmark
import org.mashup.takoyaki.ui.adapter.BookmarkAdapter
import android.view.View
import org.mashup.takoyaki.presenter.mypage.bookmark.BookmarkPresenter
import org.mashup.takoyaki.presenter.mypage.bookmark.BookmarkView

class BookmarkActivity : AppCompatActivity(), BookmarkView {
    val presenter: BookmarkPresenter = BookmarkPresenter(this)

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BookmarkActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        presenter.onCreate()
    }

    override fun setAdapter() {
        with(recyclerview) {
            layoutManager = android.support.v7.widget.GridLayoutManager(context, 2)
            BookmarkAdapter(presenter::onContentClicked).let {
                adapter = it
            }
        }
    }

    override fun setDataToAdapter(bookmarks: List<Bookmark>) {
        (recyclerview.adapter as? BookmarkAdapter)?.run {
            setData(bookmarks)
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
