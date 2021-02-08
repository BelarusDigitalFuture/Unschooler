package unschooler.code.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "dd.MM.yyyy"

@SuppressLint("SimpleDateFormat")
fun Date?.toFormat(format: String = DATE_FORMAT): String {
    if (this == null)
        return ""
    return SimpleDateFormat(format).format(this)
}

fun Date.addDays(day: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_YEAR, day)
    return calendar.time
}