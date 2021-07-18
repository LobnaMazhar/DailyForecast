package lobna.parentaps.daily.forecast.ui.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.net.Uri
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.data.ForecastResponse
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse
import lobna.parentaps.daily.forecast.repository.MainRepository
import kotlin.math.roundToInt

/**
 * Implementation of App Widget functionality.
 */
class TodayForecastWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.today_forecast_widget)

    CoroutineScope(Dispatchers.IO).launch {
        val response = MainRepository.getCities(context)
        if (response.isNotEmpty()) {
            val city = response[0].name
            val response = MainRepository.getDailyForecast(city, 1)
            when (response) {
                is OpenWeatherResponse.ErrorResponse, is OpenWeatherResponse.ExceptionResponse -> {
                }
                is OpenWeatherResponse.DataResponse<*> -> {
                    withContext(Dispatchers.Main) {
                        (response.data as? ForecastResponse)?.run {
                            if (list.isNotEmpty()) {
                                list[0].run {
                                    views.setTextViewText(
                                        R.id.temp_text,
                                        "${temp.max.roundToInt()}/${temp.min.roundToInt()}°C"
                                    )
                                    views.setImageViewUri(
                                        R.id.description_icon, Uri.parse(
                                            "https://openweathermap.org/img/wn/${weather[0].icon}.png"
                                        )
                                    )
                                    views.setTextViewText(
                                        R.id.description_text,
                                        weather[0].description
                                    )

                                    // Instruct the widget manager to update the widget
                                    appWidgetManager.updateAppWidget(appWidgetId, views)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}