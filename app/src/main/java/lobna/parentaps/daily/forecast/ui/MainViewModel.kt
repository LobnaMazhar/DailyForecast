package lobna.parentaps.daily.forecast.ui

import android.app.AlertDialog
import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.data.CityModel
import lobna.parentaps.daily.forecast.data.DailyForecast
import lobna.parentaps.daily.forecast.data.ForecastResponse
import lobna.parentaps.daily.forecast.data.OpenWeatherResponse
import lobna.parentaps.daily.forecast.repository.OpenWeatherRepository
import lobna.parentaps.daily.forecast.utils.LocationUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val cityObservable = ObservableField<CityModel>()
    val isFavouriteObservable = ObservableBoolean(false)

    private val days = arrayListOf<DailyForecast>()
    val dayForecastAdapter = DayForecastAdapter(days)

    val requestPermissionClick = MutableLiveData<Boolean>()

    fun init(fusedLocationClient: FusedLocationProviderClient) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = OpenWeatherRepository.getCities(getApplication())
            withContext(Dispatchers.Main) {
                if (response.isEmpty()) {
                    detectLocation(fusedLocationClient)
                } else {
                    selectCity(response[0], true)
                }
            }
        }
    }

    private fun selectCity(city: CityModel, isFavourite: Boolean = false) {
        cityObservable.set(city)
        isFavouriteObservable.set(isFavourite)
        getData(city.name)
    }

    fun detectLocation(fusedLocationClient: FusedLocationProviderClient) {
        LocationUtils.getCurrentLocation(getApplication(), fusedLocationClient, {
            requestPermissionClick.postValue(true)
        }, { latLng ->
            latLng?.run { getData(latitude, longitude) } ?: getData(saveCity = true)
        })
    }

    private fun getData(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = OpenWeatherRepository.getDailyForecast(latitude, longitude)
            withContext(Dispatchers.Main) { bindResponse(response, true) }
        }
    }

    fun getData(city: String = "London, UK", saveCity: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = OpenWeatherRepository.getDailyForecast(city)
            withContext(Dispatchers.Main) { bindResponse(response, saveCity) }
        }
    }

    private fun bindResponse(response: OpenWeatherResponse, saveCity: Boolean) {
        when (response) {
            is OpenWeatherResponse.ErrorResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.ExceptionResponse ->
                Toast.makeText(getApplication(), response.message, Toast.LENGTH_LONG).show()
            is OpenWeatherResponse.DataResponse<*> -> {
                (response.data as? ForecastResponse)?.run {
                    cityObservable.set(city)
                    if (saveCity) favourite()

                    days.clear()
                    days.addAll(list)
                    dayForecastAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun selectCity(view: View) {
        val builder = AlertDialog.Builder(view.context, R.style.MyAlertDialogTheme)
        builder.setView(R.layout.dialog_favourite_city)

        val alertDialog = builder.create()

        viewModelScope.launch(Dispatchers.IO) {
            val cities = OpenWeatherRepository.getCities(view.context)
            withContext(Dispatchers.Main) {
                val citiesRecycler = alertDialog.findViewById<RecyclerView>(R.id.cities_recycler)
                citiesRecycler.adapter = CityAdapter(cities, object : CityInterface {
                    override fun onCityClick(city: CityModel) {
                        selectCity(city, true)
                        alertDialog.dismiss()
                    }
                })
            }
        }

        alertDialog.show()
    }

    fun favourite() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavouriteObservable.get())
                OpenWeatherRepository.deleteCity(getApplication(), cityObservable.get()!!)
            else
                OpenWeatherRepository.saveCity(getApplication(), cityObservable.get()!!)
            isFavouriteObservable.set(!isFavouriteObservable.get())
        }
    }

    fun search(view: View) {

    }
}