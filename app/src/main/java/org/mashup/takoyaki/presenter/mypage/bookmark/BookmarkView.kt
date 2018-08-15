package org.mashup.takoyaki.presenter.mypage.bookmark

import org.mashup.takoyaki.data.remote.model.Bookmark
import org.mashup.takoyaki.presenter.View

interface BookmarkView : View {
    fun setAdapter()
    fun setDataToAdapter(bookmarks: List<Bookmark>)
}