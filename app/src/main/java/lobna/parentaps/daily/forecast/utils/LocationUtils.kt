package lobna.parentaps.daily.forecast.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import lobna.parentaps.daily.forecast.R
import lobna.parentaps.daily.forecast.utils.Utilities.showToast

object LocationUtils {

    fun getCurrentLocation(
        context: Context,
        fusedLocationClient: FusedLocationProviderClient,
        requestPermissions: () -> Unit, setLocation: (latLng: LatLng?) -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
        } else {
            fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result
                    location?.let {
                        setLocation(LatLng(location.latitude, location.longitude))
                    } ?: run {
                        context.showToast(context.getString(R.string.failed_to_get_location))
                        setLocation(null)
                    }
                } else {
                    context.showToast(context.getString(R.string.failed_to_get_location))
                    setLocation(null)
                }
            }
        }
    }
}