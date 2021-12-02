package vin.extractor.pbt

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit.DAYS
import java.util.*
import java.util.Calendar.DAY_OF_YEAR

@Suppress("Unused")
object Dates {
    private const val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private val dateFormat: DateFormat = SimpleDateFormat(pattern)

    fun toString(d: Date): String = dateFormat.format(d)
    fun fromString(s: String): Date = dateFormat.parse(s)

    fun nextDay(s: String): String {
        val calendar = Calendar.getInstance()
        calendar.time = fromString(s)
        calendar.add(DAY_OF_YEAR, 1)
        return toString(calendar.time)
    }

    fun nextDayJava8(s: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDateTime.parse(s, formatter)
        val date = localDate.plus(1, DAYS)
        return date.format(formatter)
    }
}
