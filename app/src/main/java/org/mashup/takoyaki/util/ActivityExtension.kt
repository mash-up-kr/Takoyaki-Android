package org.mashup.takoyaki.util

import android.app.Activity
import android.support.annotation.StringRes
import android.widget.Toast


/**
 * Created by jonghunlee on 2018-08-03.
 */

fun Activity.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}