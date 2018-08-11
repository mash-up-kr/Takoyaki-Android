package org.mashup.takoyaki.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.item_menu_and_price_list.view.*
import org.mashup.takoyaki.R


/**
 * Created by whdcn on 2018-07-27.
 */
//data class MenuAndPrice(val menu: String, val price: Int)


class MenuAndPriceLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val ACTION_ADD = "ACTION_ADD"
        private const val ACTION_REMOVE = "ACTION_REMOVE"
    }

    init {
        orientation = LinearLayout.VERTICAL
        addView()
    }

    private fun addView() {
        val view = LayoutInflater
                .from(context)
                .inflate(R.layout.item_menu_and_price_list, this, false)

        view.btAddAndRemove.setOnClickListener {
            when (it.tag) {
                ACTION_ADD -> {
                    if (checkInputData(view) && checkInputPrice(view)) {
                        addView()
                        hideSoftInput()
                    }
                }
                ACTION_REMOVE -> {
                    removeView(view.tag as Int)
                    hideSoftInput()
                }
            }
        }

        addView(view)
        changeButtonAction()
    }

    private fun removeView(position: Int) {
        removeViewAt(position)
        changeButtonAction()
    }

    private fun changeButtonAction() {
        for (position in 0 until childCount) {
            val view = getChildAt(position)
            view.tag = position

            if (position == childCount - 1) {
                view.btAddAndRemove.text = "+"
                view.btAddAndRemove.tag = ACTION_ADD
            } else {
                view.btAddAndRemove.text = "-"
                view.btAddAndRemove.tag = ACTION_REMOVE
            }
        }
    }

    private fun hideSoftInput() {
        val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun checkInputData(view: View): Boolean {
        if (view.etMenu.text.toString().isEmpty() ||
                view.etPrice.text.toString().isEmpty()) {
            Toast.makeText(context,
                           R.string.need_input_menu_and_price,
                           Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun checkInputPrice(view: View): Boolean {
        if (view.etPrice.text.toString().toIntOrNull() == null) {
            Toast.makeText(context,
                           R.string.need_input_price_numeric,
                           Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}