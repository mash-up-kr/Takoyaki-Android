package org.mashup.takoyaki.util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import org.mashup.takoyaki.R
import java.math.BigInteger
import java.text.NumberFormat
import java.util.*

class Resources(val context: Context?) {

    companion object {
        private var instance: Resources? = null

        @Synchronized
        fun getInstance(context: Context?): Resources {
            if (instance == null) {
                instance = Resources(context)
            }
            return instance!!
        }
    }

    fun getStyledAttribute(@AttrRes resourceId: Int, index: Int = 0): Drawable? {
        return context?.obtainStyledAttributes(intArrayOf(resourceId))?.getDrawable(index)
    }

    fun getString(@StringRes resourceId: Int): String {
        return getString(resourceId, "")
    }

    fun getString(@StringRes resourceId: Int, defaultValue: String): String {
        return context?.resources?.getString(resourceId) ?: defaultValue
    }

    fun getText(@StringRes resourceId: Int): CharSequence {
        return getText(resourceId, "")
    }

    fun getText(@StringRes resourceId: Int, defaultValue: String): CharSequence {
        return context?.resources?.getText(resourceId, defaultValue) ?: defaultValue
    }

    fun getColor(@ColorRes resourceId: Int): Int {
        return context?.let {
            ContextCompat.getColor(it, resourceId)
        } ?: 0
    }

    fun getDrawable(@DrawableRes resourceId: Int): Drawable? {
        return context?.let {
            ContextCompat.getDrawable(it, resourceId)
        }
    }

    fun getDimensionInPixel(@DimenRes resourceId: Int): Int {
        return context?.resources?.getDimensionPixelSize(resourceId) ?: 0
    }

    fun getInteger(@IntegerRes resourceId: Int): Int {
        return context?.resources?.getInteger(resourceId) ?: 0
    }
}

val Context?.r: Resources get() = Resources(this)

val Fragment.r: Resources get() = Resources(context)

val View.r: Resources get() = Resources(context)

val Int.formattedString: String
    get() = String.format(Locale.getDefault(), "%,d", this)

val Long.formattedString: String
    get() = String.format(Locale.getDefault(), "%,d", this)

val BigInteger.formattedString: String
    get() = String.format(Locale.getDefault(), "%,d", this)

val Float.formattedString: String
    get() = String.format(Locale.getDefault(), "%,.2f", this)

val Double.formattedString: String
    get() = String.format(Locale.getDefault(), "%,.2f", this)

val Double.formattedString1: String
    get() = String.format(Locale.getDefault(), "%,.1f", this)

val Float.formattedString1: String
    get() = String.format(Locale.getDefault(), "%,.1f", this)

val Int.formattedSignedString: String
    get() = with(formattedString) {
        if (this@formattedSignedString > 0) {
            "+$this"
        } else this
    }

val Long.formattedSignedString: String
    get() = with(formattedString) {
        if (this@formattedSignedString > 0) {
            "+$this"
        } else this
    }

val BigInteger.formattedSignedString: String
    get() = with(formattedString) {
        if (this@formattedSignedString > BigInteger.ZERO) {
            "+$this"
        } else this
    }

val Float.formattedSignedString: String
    get() = with(formattedString) {
        if (this@formattedSignedString > 0) {
            "+$this"
        } else this
    }

val Double.formattedSignedString: String
    get() = with(formattedString) {
        if (this@formattedSignedString > 0) {
            "+$this"
        } else this
    }

val Int.formattedSpaceSignedString: String
    get() = with(formattedString) {
        if (this@formattedSpaceSignedString > 0) {
            "+ $this"
        } else this.replace("-", "- ")
    }

val Long.formattedSpaceSignedString: String
    get() = with(formattedString) {
        if (this@formattedSpaceSignedString > 0) {
            "+ $this"
        } else this.replace("-", "- ")
    }

val BigInteger.formattedSpaceSignedString: String
    get() = with(formattedString) {
        if (this@formattedSpaceSignedString > BigInteger.ZERO) {
            "+ $this"
        } else this.replace("-", "- ")
    }

val Float.formattedSpaceSignedString: String
    get() = with(formattedString) {
        if (this@formattedSpaceSignedString > 0) {
            "+ $this"
        } else this.replace("-", "- ")
    }

val Double.formattedSpaceSignedString: String
    get() = with(formattedString) {
        if (this@formattedSpaceSignedString > 0) {
            "+ $this"
        } else this.replace("-", "- ")
    }

fun Long.formatStringWithCurrency(currency: String, maximumFractionDigits: Int = 0): String {
    return NumberFormat.getCurrencyInstance().apply {
        this.currency = Currency.getInstance(currency)
        this.maximumFractionDigits = maximumFractionDigits
    }.format(this)
}

fun TypedArray.use(block: (typedArray: TypedArray) -> Unit) {
    try {
        block(this)
    } finally {
        recycle()
    }
}