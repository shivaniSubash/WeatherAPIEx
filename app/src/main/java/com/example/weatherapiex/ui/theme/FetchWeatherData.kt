package com.example.weatherapiex.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import com.example.weatherapiex.data.WeatherRepository
import com.example.weatherapiex.data.WeatherResponse
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

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
            if (location != null) {
                coroutineScope.launch {
                    try {
                        val weatherResponse = WeatherRepository.getCurrentWeather(
                            location.latitude, location.longitude, "d42aae37013c4b0d56a7b6c9b2e222d8"
                        )
                        onWeatherFetched(weatherResponse)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "Failed to load weather data: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                // Try requesting location updates if last known location is unavailable
                fusedLocationClient.requestLocationUpdates(
                    LocationRequest.create().apply {
                        interval = 10000
                        fastestInterval = 5000
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    },
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val newLocation = locationResult.lastLocation
                            if (newLocation != null) {
                                coroutineScope.launch {
                                    try {
                                        val weatherResponse = WeatherRepository.getCurrentWeather(
                                            newLocation.latitude, newLocation.longitude, "d42aae37013c4b0d56a7b6c9b2e222d8"
                                        )
                                        onWeatherFetched(weatherResponse)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                        Toast.makeText(context, "Failed to load weather data: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                                fusedLocationClient.removeLocationUpdates(this)
                            } else {
                                Toast.makeText(context, "Location still not available", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    null
                )
            }
        }
    }
}
