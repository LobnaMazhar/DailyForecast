package lobna.parentaps.daily.forecast.ui.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import lobna.parentaps.daily.forecast.data.DailyForecast
import lobna.parentaps.daily.forecast.utils.DateUtils
import kotlin.math.roundToInt

class DayForecastItemViewModel(val item: DailyForecast) {

    val isTodayObservable = ObservableBoolean(DateUtils.isToday(item.dt))
    val dateObservable = ObservableField(DateUtils.formatDate(item.dt))
    val sunriseObservable =
        ObservableField(if (isTodayObservable.get()) DateUtils.formatTime(item.sunrise) else "")
    val sunsetObservable =
        ObservableField(if (isTodayObservable.get()) DateUtils.formatTime(item.sunset) else "")
    val temperatureObservable =
        ObservableField("${item.temp.max.roundToInt()}/${item.temp.min.roundToInt()}")
    val speedObservable = ObservableField("${item.speed.roundToInt()}")
}