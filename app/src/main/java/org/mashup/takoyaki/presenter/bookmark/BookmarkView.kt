package org.mashup.takoyaki.presenter.bookmark

import org.mashup.takoyaki.data.remote.model.Bookmark

interface BookmarkView {
    fun setDataToAdapter(bookmarks: List<Bookmark>)
}
