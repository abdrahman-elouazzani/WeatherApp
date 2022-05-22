package com.example.weatherapp.data.repository

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.model.Location
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ForecastRepositoryImp(private val weatherApiService: WeatherApiService):ForecastRepository {

    /*
    *
    *  get current weather by location from the service
    * */
    override fun getCurrentWeather(location: String): io.reactivex.Observable<CurrentWeather> {
        return weatherApiService.getCurrentWeather(location)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        }

}