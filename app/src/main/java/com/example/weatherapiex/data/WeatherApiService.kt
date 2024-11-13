package com.example.weatherapiex.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
        //Specifies that the method will make a GET request to the weather endpoint of the API.
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): WeatherResponse
        //The function returns a WeatherResponse object, which is expected to be a data class representing the response structure from the API.
}