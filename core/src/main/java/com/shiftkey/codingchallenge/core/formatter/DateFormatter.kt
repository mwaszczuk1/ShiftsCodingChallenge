package com.shiftkey.codingchallenge.core.formatter

import android.content.Context
import com.shiftkey.codingchallenge.core.R
import java.time.LocalDate
import java.util.*

/**
 * Returns date label in format ->  (DAY IN WEEK), (MONTH_NAME) (DAY_IN_MONTH)
 *  E.g. If date is on today it returns Today, February 12 otherwise Monday, February 12
 */
fun LocalDate.getDateLabel(context: Context): String {
    val dayName =
        if (this == LocalDate.now())
            context.getString(R.string.today)
        else
            this.dayOfWeek.name
    val monthName = this.month.name.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    return "$dayName, $monthName ${this.dayOfMonth}"
}
