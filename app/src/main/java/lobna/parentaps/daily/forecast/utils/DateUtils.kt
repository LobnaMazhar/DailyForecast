package lobna.parentaps.daily.forecast.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    private val TAG = DateUtils.javaClass.simpleName

    fun isToday(timestamp: Long): Boolean {
        return android.text.format.DateUtils.isToday(timestamp * 1000L)
    }

    fun formatDate(timestamp: Long): String {
        val date = Date(timestamp * 1000L)

        val timeFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
        return timeFormat.format(date)
    }

    fun formatTime(timestamp: Long): String {
        val date = Date(timestamp * 1000L)

        val timeFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return timeFormat.format(date)
    }
}