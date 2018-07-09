package org.mashup.takoyaki.ui.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*


/**
 * Created by jonghunlee on 2018-07-06.
 */
@Suppress("UNCHECKED_CAST")
abstract class RecycledPagerAdapter<VH : RecycledPagerAdapter.ViewHolder> : PagerAdapter() {

    abstract class ViewHolder(val itemView: View)

    private val destroyedItems: Queue<VH> = LinkedList<VH>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var viewHolder = destroyedItems.poll()
        if (viewHolder != null) {
            container.addView(viewHolder.itemView)
            onBindViewHolder(viewHolder, position)
        } else {
            viewHolder = onCreateViewHolder(container)
            onBindViewHolder(viewHolder, position)
            container.addView(viewHolder.itemView)
        }

        return viewHolder
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView((obj as VH).itemView)
        destroyedItems.add(obj)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return (obj as VH).itemView === view
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): VH

    abstract fun onBindViewHolder(viewHolder: VH, position: Int)
}