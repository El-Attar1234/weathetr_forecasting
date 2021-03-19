package com.example.forecasting.data.network.data_source

import androidx.lifecycle.LiveData
import com.example.forecasting.data.local_db.entity.CurrentweatherResponse
import com.example.forecasting.data.local_db.entity.Day
import com.example.forecasting.data.local_db.entity.FavouriteWeatherResponse
import com.example.forecasting.data.local_db.entity.FutureWeatherResponse

interface WeatherDataSource {
    val downloadedCurrentWeather:LiveData<CurrentweatherResponse>
    suspend fun fetchCurrentWeather(lat:String,lon:String,ex:String,language:String)

    val downloadedFutureWeather:LiveData<List<Day>>
    suspend fun fetchFutureWeather(lat:String,lon:String,ex:String)

    val downloadedFavouriteWeather:LiveData<FavouriteWeatherResponse>
    suspend fun fetchFavouriteWeather(lat:String,lon:String,ex:String)

}