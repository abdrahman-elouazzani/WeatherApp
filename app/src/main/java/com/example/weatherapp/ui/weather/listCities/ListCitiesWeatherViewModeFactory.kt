package com.example.weatherapp.ui.weather.listCities

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.repository.ForecastRepository

class ListCitiesWeatherViewModeFactory(val context: Context):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListCitiesWeatherViewModel(context) as T
    }
}