package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import com.example.weatherapp.data.model.CurrentWeather
import io.reactivex.Observable
import io.reactivex.Observer

interface ForecastRepository {
    fun getCurrentWeather(location:String): Observable<CurrentWeather>

}