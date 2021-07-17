package lobna.parentaps.daily.forecast.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class ForecastResponse(
    val city: CityModel,
    val list: List<DailyForecast>
)

@Entity
data class CityModel(
    @PrimaryKey val id: Int,
    @ColumnInfo val name: String
)

data class DailyForecast(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: TemperatureModel,
    val pressure: Int,
    val humidity: Int,
    val weather: List<WeatherModel>,
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
