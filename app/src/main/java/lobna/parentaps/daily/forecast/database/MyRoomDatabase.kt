package lobna.parentaps.daily.forecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.DailyForecast
import lobna.parentaps.daily.forecast.database.dao.OpenWeatherDao

@Database(entities = [CityModel::class, DailyForecast::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: MyRoomDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: getInstance(context).also { instance = it }
        }

        private fun getInstance(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, MyRoomDatabase::class.java, "daily_forecast"
            ).build()

    }

    abstract fun openWeather(): OpenWeatherDao
}