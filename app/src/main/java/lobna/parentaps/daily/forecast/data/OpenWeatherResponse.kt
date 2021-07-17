package lobna.parentaps.daily.forecast.data

sealed class OpenWeatherResponse {
    data class ErrorResponse(val code: Int, val message: String) : OpenWeatherResponse()
    data class ExceptionResponse(val message: String?) : OpenWeatherResponse()
    data class DataResponse<T>(val data: T) : OpenWeatherResponse()
}