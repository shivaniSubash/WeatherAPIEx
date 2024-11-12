package com.example.weatherapiex.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.*
import android.content.Context
import androidx.compose.foundation.layout.Column
import com.example.weatherapiex.data.WeatherResponse

@Composable
fun WeatherScreen(context: Context) {
    var weather by remember { mutableStateOf<WeatherResponse?>(null) }

    FetchWeatherData(context) {
        weather = it
    }

    if (weather != null) {
        val fahrenheitTemp = (weather!!.main.temp - 273.15) * 9/5 + 32
        Column {
            // Display temperature in Fahrenheit, formatted to 1 decimal place
            Text(text = "Temperature: ${"%.1f".format(fahrenheitTemp)}°F")
            Text(text = "Condition: ${weather!!.weather[0].description}")
        }
    } else {
        Text(text = "Loading...")
    }
}