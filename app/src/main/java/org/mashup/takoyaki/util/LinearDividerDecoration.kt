package org.mashup.takoyaki.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v7.widget.RecyclerView
import android.view.View
import org.mashup.takoyaki.R

class LinearDividerDecoration(
        context: Context,
        val showAtLastItem: Boolean = true,
        @ColorRes colorResId: Int = R.color.gray,
        @DimenRes dimenResId: Int = R.dimen.divider_100,
        @DimenRes offsetResId: Int = R.dimen.divider_offset_0
) : RecyclerView.ItemDecoration() {

    val paint = Paint().apply {
        color = context.r.getColor(colorResId)
    }
    val thick = context.r.getDimensionInPixel(dimenResId)
    val offset = context.r.getDimensionInPixel(offsetResId)

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.bottom = thick
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        if (showAtLastItem) {
            (0..parent!!.childCount - 1)
                    .map { parent.getChildAt(it) }
                    .forEach {
                        val left = it.left + offset
                        val right = it.right
                        val top = it.bottom
                        val bottom = it.bottom + thick

                        c?.drawRect(
                                left.toFloat(),
                                top.toFloat(),
                                right.toFloat(),
                                bottom.toFloat(),
                                paint
                        )
                    }
        } else {
            if (parent!!.childCount > 1) {
                (0..parent.childCount - 2)
                        .map { parent.getChildAt(it) }
                        .forEach {
                            val left = it.left + offset
                            val right = it.right
                            val top = it.bottom
                            val bottom = it.bottom + thick

                            c?.drawRect(
                                    left.toFloat(),
                                    top.toFloat(),
                                    right.toFloat(),
                                    bottom.toFloat(),
                                    paint
                            )
                        }
            }
        }
    }

}
