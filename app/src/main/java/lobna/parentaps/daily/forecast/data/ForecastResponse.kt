package lobna.parentaps.daily.forecast.data

import androidx.room.*


data class ForecastResponse(
    val city: CityModel,
    @Relation(parentColumn = "name", entityColumn = "cityName", entity = DailyForecast::class)
    val list: List<DailyForecast>
)

@Entity
data class CityModel(
    @PrimaryKey val name: String
)

@Entity(primaryKeys = ["cityName", "dt"])
data class DailyForecast(
    var cityName: String,
    @ColumnInfo val dt: Long,
    @ColumnInfo val sunrise: Long,
    @ColumnInfo val sunset: Long,
    @Embedded val temp: TemperatureModel,
    @ColumnInfo val pressure: Int,
    @ColumnInfo val humidity: Int,
    @ColumnInfo var weather: List<WeatherModel>,
    @ColumnInfo val speed: Double,
    @ColumnInfo val clouds: Int
)

data class TemperatureModel(
    val min: Double,
    val max: Double
)

data class WeatherModel(
    val description: String,
    val icon: String
)
