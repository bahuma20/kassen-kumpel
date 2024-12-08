package io.bahuma.kassenkumpel.utils

import android.icu.text.DateFormat
import java.time.Instant
import java.util.Date

fun formatDate(date: Instant): String {
    return DateFormat.getDateTimeInstance().format(Date.from(date))
}