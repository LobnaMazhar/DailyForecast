package lobna.parentaps.daily.forecast.repository

import android.content.Context
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse

interface MainInterface {

    suspend fun saveCity(context: Context, city: CityModel): OpenWeatherResponse
    suspend fun getCities(context: Context): List<CityModel>
    suspend fun ifCityExists(context: Context, id: Int): Boolean
    suspend fun deleteCity(context: Context, city: CityModel)
    suspend fun getCount(context: Context): Int

    suspend fun getDailyForecast(latitude: Double, longitude: Double): OpenWeatherResponse
    suspend fun getDailyForecast(city: String, nDays: Int = 5): OpenWeatherResponse
}