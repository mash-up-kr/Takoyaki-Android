package org.mashup.takoyaki.util

import java.util.*

interface StickyHeader {
    val id: Long
}

class DailyHeader(date: Date) : StickyHeader {
    override val id: Long =
            Times.from(date)
                    .startOfDay()
                    .timeInMillis
}
