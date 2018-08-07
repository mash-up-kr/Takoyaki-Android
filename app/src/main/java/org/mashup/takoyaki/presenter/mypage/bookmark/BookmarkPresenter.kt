package org.mashup.takoyaki.presenter.mypage.bookmark

import android.util.Log
import org.mashup.takoyaki.data.remote.model.Bookmark

class BookmarkPresenter(val view: BookmarkView) {
    var garaData: List<Bookmark> = listOf()

    fun onCreate() {
        view.setAdapter()
        view.setDataToAdapter(garaData)
    }

    fun onContentClicked(bookmark: Bookmark) {
        Log.e("#", "onContentClicked")
    }

}
