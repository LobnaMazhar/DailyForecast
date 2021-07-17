package lobna.parentaps.daily.forecast.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.parentaps.daily.forecast.data.ForecastResponse
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse
import lobna.parentaps.daily.forecast.repository.OpenWeatherRepository
import lobna.parentaps.daily.forecast.utils.LocationUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val days = arrayListOf<ForecastResponse.DailyForecast>()
    val dayForecastAdapter = DayForecastAdapter(days)

    val requestPermissionClick = MutableLiveData<Boolean>()

    fun detectLocation(fusedLocationClient: FusedLocationProviderClient) {
        LocationUtils.getCurrentLocation(getApplication(), fusedLocationClient, {
            requestPermissionClick.postValue(true)
        }, { latLng ->
            latLng?.run { getData(latitude, longitude) } ?: setLocation()
        })
    }

    fun setLocation() {
        getData("London, UK")
    }

    private fun getData(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = OpenWeatherRepository.getDailyForecast(latitude, longitude)
            withContext(Dispatchers.Main) { bindResponse(response) }
        }
    }

    private fun getData(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = OpenWeatherRepository.getDailyForecast(city)
            withContext(Dispatchers.Main) { bindResponse(response) }
        }
    }

    private fun bindResponse(response: OpenWeatherResponse) {
        when (response) {
            is OpenWeatherResponse.ErrorResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.ExceptionResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.DataResponse<*> -> {
                (response.data as? ForecastResponse)?.run {
                    days.clear()
                    days.addAll(list)
                    dayForecastAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}