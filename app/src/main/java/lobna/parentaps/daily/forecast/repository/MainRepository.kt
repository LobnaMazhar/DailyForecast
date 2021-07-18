package lobna.parentaps.daily.forecast.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse
import lobna.parentaps.daily.forecast.database.MyRoomDatabase
import lobna.parentaps.daily.forecast.network.MyRetrofitClient
import lobna.parentaps.daily.forecast.network.WeatherApiInterface

object MainRepository : MainInterface {

    private var weatherApi: WeatherApiInterface =
        MyRetrofitClient.createService(WeatherApiInterface::class.java)

    override suspend fun saveCity(context: Context, city: CityModel): OpenWeatherResponse {
        return if (getCount(context) == 5)
            OpenWeatherResponse.ErrorResponse(0, context.getString(R.string.reached_max))
        else {
            MyRoomDatabase.invoke(context).openWeather().insertCity(city)
            OpenWeatherResponse.DataResponse(true)
        }
    }

    override suspend fun getCities(context: Context): List<CityModel> {
        return MyRoomDatabase.invoke(context).openWeather().readCities()
    }

    override suspend fun ifCityExists(context: Context, id: Int): Boolean {
        return MyRoomDatabase.invoke(context).openWeather().getCity(id).isNotEmpty()
    }

    override suspend fun deleteCity(context: Context, city: CityModel) {
        MyRoomDatabase.invoke(context).openWeather().deleteCity(city)
    }

    override suspend fun getCount(context: Context): Int {
        return MyRoomDatabase.invoke(context).openWeather().getCount()
    }

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

    override suspend fun getDailyForecast(city: String, nDays: Int): OpenWeatherResponse {
        return try {
            val response = weatherApi.dailyForecast(city, nDays)

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