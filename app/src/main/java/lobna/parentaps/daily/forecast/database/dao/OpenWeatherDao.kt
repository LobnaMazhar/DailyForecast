package lobna.parentaps.daily.forecast.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import lobna.parentaps.daily.forecast.data.CityModel

@Dao
interface OpenWeatherDao {

    @Insert
    suspend fun insertCity(city: CityModel)

    @Query("Select * from CityModel where id = :id")
    suspend fun getCity(id: Int): List<CityModel>

    @Query("Select * from CityModel")
    suspend fun readCities(): List<CityModel>

    @Delete
    suspend fun deleteCity(city: CityModel)

    @Query("Select COUNT(id) from CityModel")
    suspend fun getCount(): Int
}