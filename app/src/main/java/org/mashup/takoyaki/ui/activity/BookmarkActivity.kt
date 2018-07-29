package org.mashup.takoyaki.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_bookmark.*
import org.mashup.takoyaki.R
import org.mashup.takoyaki.data.remote.model.Bookmark
import org.mashup.takoyaki.ui.adapter.BookmarkAdapter

class BookmarkActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, BookmarkActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        setAdapter()
    }

    fun setAdapter() {
        val items = arrayListOf<Bookmark>()
        items.add(Bookmark("asdfasd", "asdfasdfas", true))
        items.add(Bookmark("asdfasd", "asdfasdfas", true))
        items.add(Bookmark("asdfasd", "asdfasdfas", true))

        with(recyclerview) {
            layoutManager = android.support.v7.widget.GridLayoutManager(context, 2)
            BookmarkAdapter(items).let {
                adapter = it
            }
        }

    }
}
