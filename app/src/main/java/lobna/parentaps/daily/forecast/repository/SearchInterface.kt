package lobna.parentaps.daily.forecast.repository

import android.content.Context
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse

interface SearchInterface {

    suspend fun getCities(city: String): OpenWeatherResponse
}