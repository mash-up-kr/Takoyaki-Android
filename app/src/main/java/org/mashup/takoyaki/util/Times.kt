package org.mashup.takoyaki.util

import java.text.SimpleDateFormat
import java.util.*

object Times {
    val standardInitialDayOfMonth = 1

    private val year = Calendar.YEAR
    private val month = Calendar.MONTH
    private val weekOfYear = Calendar.WEEK_OF_YEAR
    private val dayOfWeek = Calendar.DAY_OF_WEEK
    private val dayOfMonth = Calendar.DAY_OF_MONTH
    private val dayOfYear = Calendar.DAY_OF_YEAR
    private val hourOfDay = Calendar.HOUR_OF_DAY
    private val minute = Calendar.MINUTE
    private val second = Calendar.SECOND
    private val millisecond = Calendar.MILLISECOND

    val aSecondInterval = 1000L
    val aMinuteInterval = aSecondInterval * 60
    val anHourInterval = aMinuteInterval * 60
    val aDayInterval = anHourInterval * 24
    val aWeekInterval = aDayInterval * 7

    enum class PeriodUnit {
        MONTHLY,
        WEEKLY,
        DAILY,
        YEARLY,
        RECENT_THREE_MONTHS,
        RECENT_SIX_MONTHS,
        CUSTOM
    }

    fun getMonths(fromDate: Date, toDate: Date, initialDayOfMonth: Int = 1): Int {
        val fromYearMonth = from(fromDate).let {
            if (it.get(Calendar.DATE) < initialDayOfMonth) {
                it.add(Calendar.MONTH, -1)
            }
            Pair(it.get(Calendar.YEAR), it.get(Calendar.MONTH))
        }
        val toYearMonth = from(toDate).let {
            if (it.get(Calendar.DATE) < initialDayOfMonth) {
                it.add(Calendar.MONTH, -1)
            }
            Pair(it.get(Calendar.YEAR), it.get(Calendar.MONTH))
        }

        return (toYearMonth.first - fromYearMonth.first) * 12 + (toYearMonth.second - fromYearMonth.second)
    }

    fun getCurrentTime(locale: Locale = Locale.getDefault()): Calendar {
        return Calendar.getInstance(locale)
    }

    fun getPrevYearMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month > 1) {
            year to month - 1
        } else {
            year - 1 to 12
        }
    }

    fun getNextYearMonth(year: Int, month: Int): Pair<Int, Int> {
        return if (month < 12) {
            year to month + 1
        } else {
            year + 1 to 1
        }
    }

    fun from(date: Date, locale: Locale = Locale.getDefault()): Calendar {
        return getCurrentTime(locale = locale).apply {
            time = date
        }
    }

    fun from(timeInMilliseconds: Long?, locale: Locale = Locale.getDefault()): Calendar {
        return if (timeInMilliseconds != null) {
            getCurrentTime(locale = locale).apply {
                timeInMillis = timeInMilliseconds
            }
        } else {
            getCurrentTime(locale = locale)
        }
    }

    fun from(year: Int, calendarMonth: Int, initialDayOfMonth: Int): Calendar {
        return getCurrentTime().apply {
            set(year, calendarMonth, 1)
            set(Calendar.DATE, Math.min(initialDayOfMonth, getActualMaximum(dayOfMonth)))
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }

    fun splitByMonth(from: Date, to: Date, splittingMonth: Int): List<Pair<Date, Date>> {
        return splitByMonth(Times.from(from) to Times.from(to), splittingMonth).map {
            it.first.time to it.second.time
        }
    }

    fun splitByMonth(fromTo: Pair<Calendar, Calendar>, monthForSplit: Int): List<Pair<Calendar, Calendar>> {
        val to = Times.from(fromTo.first.timeInMillis).apply {
            add(Calendar.MONTH, monthForSplit)
            add(Calendar.DAY_OF_MONTH, -1)
        }

        val from = Times.from(to.time).apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        if (to < fromTo.second) {
            return listOf(
                    listOf(Pair(fromTo.first, to)),
                    splitByMonth(
                            Pair(from, fromTo.second),
                            monthForSplit
                    )
            ).flatMap { it.map { it } }
        }

        return listOf(fromTo)
    }

    fun toAnchor(
            target: Calendar,
            unit: PeriodUnit = PeriodUnit.MONTHLY,
            initialDayOfMonth: Int = standardInitialDayOfMonth,
            movement: Int = 0
    ): Calendar {
        return when (unit) {
            Times.PeriodUnit.MONTHLY -> {
                getCurrentTime()
                        .apply {
                            time = target.time

                            if (initialDayOfMonth > target.get(dayOfMonth)) {
                                add(month, -1)
                            }

                            add(month, movement)

                            set(dayOfMonth, Math.min(
                                    initialDayOfMonth,
                                    getActualMaximum(dayOfMonth)
                            ))
                        }
                        .startOfDay()
            }
            Times.PeriodUnit.WEEKLY -> {
                getCurrentTime()
                        .apply {
                            time = target.time

                            add(weekOfYear, movement)
                            set(dayOfWeek, Calendar.MONDAY)
                        }
                        .startOfDay()
            }
            Times.PeriodUnit.DAILY -> {
                getCurrentTime()
                        .apply {
                            time = target.time
                            add(dayOfYear, movement)
                        }
                        .startOfDay()
            }
            Times.PeriodUnit.YEARLY -> {
                getCurrentTime()
                        .apply {
                            time = target.time
                            add(year, movement)
                            set(dayOfYear, 1)
                        }
                        .startOfDay()
            }
            Times.PeriodUnit.RECENT_THREE_MONTHS -> {
                getCurrentTime()
                        .apply {
                            time = target.time
                            add(month, -3 * (1 - movement))
                            add(dayOfMonth, 1)
                        }
                        .startOfDay()
            }
            Times.PeriodUnit.RECENT_SIX_MONTHS -> {
                getCurrentTime()
                        .apply {
                            time = target.time
                            add(month, -6 * (1 - movement))
                            add(dayOfMonth, 1)
                        }
                        .startOfDay()
            }
            else -> getCurrentTime().startOfDay()
        }
    }

    fun toPeriod(
            target: Calendar = getCurrentTime(),
            unit: PeriodUnit = PeriodUnit.MONTHLY,
            initialDayOfMonth: Int = standardInitialDayOfMonth,
            movement: Int = 0
    ): Pair<Calendar, Calendar> {
        val anchor = toAnchor(target, unit, initialDayOfMonth, movement)

        return when (unit) {
            Times.PeriodUnit.MONTHLY -> {
                Pair(
                        anchor,
                        getCurrentTime().apply {
                            time = anchor.time

                            add(month, 1)
                            set(dayOfMonth, Math.min(
                                    initialDayOfMonth,
                                    getActualMaximum(dayOfMonth)
                            ))
                            add(second, -1)
                        }
                )
            }
            Times.PeriodUnit.WEEKLY -> {
                Pair(
                        anchor,
                        getCurrentTime().apply {
                            time = anchor.time

                            add(dayOfWeek, getActualMaximum(dayOfWeek))
                            add(second, -1)
                        }
                )
            }
            Times.PeriodUnit.DAILY -> {
                Pair(
                        anchor,
                        getCurrentTime().apply {
                            time = anchor.time

                            add(dayOfYear, 1)
                            add(second, -1)
                        }
                )
            }
            Times.PeriodUnit.YEARLY -> {
                Pair(
                        anchor,
                        getCurrentTime().apply {
                            time = anchor.time
                            add(year, 1)
                            add(second, -1)
                        }
                )
            }
            Times.PeriodUnit.RECENT_THREE_MONTHS -> {
                Pair(
                        anchor,
                        getCurrentTime()
                                .apply {
                                    time = anchor.time

                                    add(month, 3)
                                    add(second, -1)
                                }
                )
            }
            Times.PeriodUnit.RECENT_SIX_MONTHS -> {
                Pair(
                        anchor,
                        getCurrentTime()
                                .apply {
                                    time = anchor.time

                                    add(month, 6)
                                    add(second, -1)
                                }
                )
            }
            else -> Pair(
                    anchor,
                    getCurrentTime().endOfDay()
            )
        }
    }

    fun getPeriod(year: Int, month: Int, initialDayOfMonth: Int): Pair<Calendar, Calendar> {
        val anchor = from(year, month, initialDayOfMonth)
        return (
                anchor to
                        getCurrentTime()
                                .apply {
                                    time = anchor.time
                                    add(Times.month, 1)
                                    set(
                                            dayOfMonth,
                                            Math.min(initialDayOfMonth, getActualMaximum(dayOfMonth))
                                    )
                                    add(millisecond, -1)
                                }
                )
    }
}


fun Calendar.subtractInMills(other: Calendar): Long {
    return Math.abs(this.timeInMillis - other.timeInMillis)
}

fun Calendar.startOfDay(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
}

fun Calendar.startOfWeek(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.DAY_OF_WEEK, getActualMinimum(Calendar.DAY_OF_WEEK))
    }
}

fun Calendar.startOfMonth(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.DAY_OF_MONTH, 1)
    }.startOfDay()
}

fun Calendar.endOfDay(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }
}

fun Calendar.endOfWeek(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.DAY_OF_WEEK, getActualMaximum(Calendar.DAY_OF_WEEK))
    }
}

fun String.parseStringToDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date =
        SimpleDateFormat(format, Locale.getDefault()).parse(this)

fun String.parseStringToDateLong(format: String = "yyyy-MM-dd HH:mm:ss"): Long =
        SimpleDateFormat(format, Locale.getDefault())
                .parse(this).time + 9 * 60 * 60 * 1000

fun Date.getDiffDaysFromNow(): Int =
        (Math.abs(System.currentTimeMillis() - this.time) / (60 * 60 * 1000 * 24L)).toInt()

fun Date.toCalendar() = Calendar.getInstance().apply { time = this@toCalendar }

fun Date.isBetween(from: Date, to: Date): Boolean =
        !before(from) && !after(to)
