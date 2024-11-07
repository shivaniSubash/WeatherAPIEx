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
        Column {
            Text(text = "Temperature: ${weather!!.main.temp}°C")
            Text(text = "Condition: ${weather!!.weather[0].description}")
        }
    } else {
        Text(text = "Loading...")
    }
}