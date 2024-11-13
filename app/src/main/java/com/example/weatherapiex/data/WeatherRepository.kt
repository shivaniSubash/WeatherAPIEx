package com.example.weatherapiex.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        //The base URL for the OpenWeather API, used as the root for all API calls.

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
            //Includes Gson for parsing JSON responses into Kotlin objects.
        .build()

    private val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
        //Creates an instance of WeatherApiService to define HTTP request methods.
    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse {
            //calls the API to fetch weather data using latitude, longitude, and an API key.
        return apiService.getCurrentWeather(lat, lon, apiKey)
    }
}