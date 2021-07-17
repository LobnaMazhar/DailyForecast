package lobna.parentaps.daily.forecast.repository

import lobna.parentaps.daily.forecast.data.OpenWeatherResponse

interface OpenWeatherInterface {

    suspend fun getDailyForecast(latitude: Double, longitude: Double): OpenWeatherResponse
    suspend fun getDailyForecast(city: String): OpenWeatherResponse
}