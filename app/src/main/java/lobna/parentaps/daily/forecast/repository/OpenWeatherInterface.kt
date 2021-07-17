package lobna.parentaps.daily.forecast.repository

import android.content.Context
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse

interface OpenWeatherInterface {

    suspend fun saveCity(context: Context, city: CityModel)
    suspend fun getCities(context: Context): List<CityModel>
    suspend fun ifCityExists(context: Context, id: Int): Boolean
    suspend fun deleteCity(context: Context, city: CityModel)

    suspend fun getDailyForecast(latitude: Double, longitude: Double): OpenWeatherResponse
    suspend fun getDailyForecast(city: String): OpenWeatherResponse
}