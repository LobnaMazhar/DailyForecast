package lobna.parentaps.daily.forecast.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import lobna.parentaps.daily.forecast.data.WeatherModel

class Converters {

    @TypeConverter
    fun listToJsonString(value: List<WeatherModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<WeatherModel>::class.java).toList()
}