package org.mashup.takoyaki.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormattedString(form: String): String = SimpleDateFormat(form).format(this).toString()

val Date.weekOfMonth
    get() = toCalendar().run {
        val currentDayOfMonth = get(Calendar.DAY_OF_MONTH)
        val mondayOfCurrentWeek = currentDayOfMonth - when (get(Calendar.DAY_OF_WEEK)) {
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }
        return@run when {
            mondayOfCurrentWeek < -2 -> 0 // mondayOfCurrentWeek의 값이 -2보다 작을경우 그 주는 4일보다 적게 있기 때문에 0번째 주가 된다
            mondayOfCurrentWeek < 0 -> 1 // mondayOfCurrentWeek의 값이 -2보다 클경우 그 주는 4일보다 많게 있기 때문에 1번째 주가 된다
            else -> mondayOfCurrentWeek / 7 + 1
        }
    }

fun Float.toPercentage(): Int = (this * 100).toInt()
