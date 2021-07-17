package lobna.parentaps.daily.forecast.data

data class ForecastResponse(
    val city: CityModel,
    val list: List<DailyForecast>
) {

    data class CityModel(
        val id: Int,
        val name: String
    )

    data class DailyForecast(
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val temp: TemperatureModel,
        val pressure: Int,
        val humidity: Int,
        val weather: ArrayList<WeatherModel>,
        val speed: Double,
        val clouds: Int
    )

    data class TemperatureModel(
        val min: Double,
        val max: Double
    )

    data class WeatherModel(
        val description: String,
        val icon: String
    )
}