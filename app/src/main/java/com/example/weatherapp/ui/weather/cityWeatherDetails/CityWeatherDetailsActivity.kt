package com.example.weatherapp.ui.weather.cityWeatherDetails

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.repository.ForecastRepositoryImp
import com.example.weatherapp.databinding.ActivityCityWeatherDetailsBinding

class CityWeatherDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityWeatherDetailsBinding
    private lateinit var viewModel: CityWeatherDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityWeatherDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val weatherApiService = WeatherApiService()

        viewModel = ViewModelProviders.of(this,
            CityWeatherViewModelFactory(ForecastRepositoryImp(weatherApiService)))
            .get(CityWeatherDetailsViewModel::class.java)
        viewModel.currentWeather.observe(this,{
            if (viewModel.networkStatus.value?.status.equals("Success")) {
                fetchData(it)

            } else {
                Toast.makeText(this,"error connection to service",Toast.LENGTH_LONG).show()
            }

        })
        val location = intent.getStringExtra("location")
        if (location != null) {
            viewModel.getCurrentWeatherData(location)
        }
    }

    private fun fetchData(currentWeather: CurrentWeather) {

        binding.currentLocationLayout.cityName.text = currentWeather.location.name
        binding.currentLocationLayout.temp.text = currentWeather.current.temperature.toString()
        binding.currentLocationLayout.currentTime.text = currentWeather.location.localtime
        binding.currentLocationLayout.modeType.text = currentWeather.current.weatherDescriptions[0]
        binding.currentLocationLayout.windSpeed.text = "Wind Speed  ${currentWeather.current.windSpeed} km/h"
        binding.currentLocationLayout.pressure.text = "Pressure  ${currentWeather.current.pressure}"


    }
}