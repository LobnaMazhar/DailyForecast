package lobna.parentaps.daily.forecast.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse
import lobna.parentaps.daily.forecast.network.MyRetrofitClient
import lobna.parentaps.daily.forecast.network.WeatherApiInterface

object OpenWeatherRepository : OpenWeatherInterface {

    private var weatherApi: WeatherApiInterface =
        MyRetrofitClient.createService(WeatherApiInterface::class.java)

    override suspend fun getDailyForecast(
        latitude: Double, longitude: Double
    ): OpenWeatherResponse {
        return try {
            val response = weatherApi.dailyForecast(latitude, longitude)

            if (response.isSuccessful) {
                OpenWeatherResponse.DataResponse(response.body())
            } else {
                OpenWeatherResponse.ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) { e.printStackTrace() }
            OpenWeatherResponse.ExceptionResponse(e.message)
        }
    }

    override suspend fun getDailyForecast(city: String): OpenWeatherResponse {
        return try {
            val response = weatherApi.dailyForecast(city)

            if (response.isSuccessful) {
                OpenWeatherResponse.DataResponse(response.body())
            } else {
                OpenWeatherResponse.ErrorResponse(response.code(), response.message())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) { e.printStackTrace() }
            OpenWeatherResponse.ExceptionResponse(e.message)
        }
    }

}