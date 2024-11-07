package com.example.weatherapiex.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): WeatherResponse {
        return apiService.getCurrentWeather(lat, lon, apiKey)
    }
}