package org.mashup.takoyaki.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_menu_and_price_list.view.*
import org.mashup.takoyaki.R


/**
 * Created by whdcn on 2018-07-27.
 */
data class MenuAndPrice(val menu: String, val price: Int)


class MenuAndPriceLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val menuAndPrices = mutableListOf<MenuAndPrice>()

    init {
        orientation = LinearLayout.VERTICAL

        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_menu_and_price_list, this, false)
        view.btRemove.isEnabled = false

        view.btAdd.setOnClickListener { addItem() }

        addView(view)
    }

    private fun addItem() {
        val menuAndPrice = MenuAndPrice(etMenu.text.toString(), etPrice.text.toString().toInt())
        menuAndPrices.add(menuAndPrice)
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_menu_and_price_list, this, false)
        view.btRemove.isEnabled = true
        view.btRemove.setOnClickListener {
            removeViewAt(menuAndPrices.size)
            menuAndPrices.removeAt(menuAndPrices.size - 1)
        }

        view.btAdd.setOnClickListener { addItem() }

        addView(view)
    }
}