package lobna.parentaps.daily.forecast.database.dao

import androidx.room.*
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.DailyForecast

@Dao
interface OpenWeatherDao {

    @Insert
    suspend fun insertCity(city: CityModel)

    @Query("Select * from CityModel where name = :name")
    suspend fun getCity(name: String): List<CityModel>

    @Query("Select * from CityModel")
    suspend fun readCities(): List<CityModel>

    @Delete
    suspend fun deleteCity(city: CityModel)

    @Query("Select COUNT(name) from CityModel")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayForecast(forecast: DailyForecast)

    @Query("Select * from DailyForecast where cityName=:cityName")
    suspend fun readForecast(cityName: String): List<DailyForecast>

}