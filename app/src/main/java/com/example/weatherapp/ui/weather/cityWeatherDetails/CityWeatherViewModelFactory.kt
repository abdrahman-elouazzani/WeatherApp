package com.example.weatherapp.ui.weather.cityWeatherDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.repository.ForecastRepository

class CityWeatherViewModelFactory (val forecastRepository: ForecastRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CityWeatherDetailsViewModel(forecastRepository) as T
    }
}