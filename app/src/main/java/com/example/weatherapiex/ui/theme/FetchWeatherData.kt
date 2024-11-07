package com.example.weatherapiex.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import com.example.weatherapiex.data.WeatherRepository
import com.example.weatherapiex.data.WeatherResponse

@SuppressLint("MissingPermission")
@Composable
fun FetchWeatherData(
    context: Context,
    onWeatherFetched: (WeatherResponse) -> Unit
) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val coroutineScope = rememberCoroutineScope()

    LocationPermissionRequest {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                coroutineScope.launch {
                    try {
                        val weatherResponse = WeatherRepository.getCurrentWeather(
                            it.latitude, it.longitude, "YOUR_API_KEY" //replace w actual API Key
                        )
                        onWeatherFetched(weatherResponse)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}