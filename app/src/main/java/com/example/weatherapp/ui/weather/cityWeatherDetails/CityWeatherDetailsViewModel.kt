package com.example.weatherapp.ui.weather.cityWeatherDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.repository.ForecastRepository
import io.reactivex.disposables.Disposable

class CityWeatherDetailsViewModel(val forecastRepository: ForecastRepository): ViewModel() {

    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    /*
    * get the current weather bu location from the service
    * */

    fun getCurrentWeatherData(location: String) {
        forecastRepository.getCurrentWeather(location)
            .subscribe(object :io.reactivex.Observer<CurrentWeather>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CurrentWeather) {
                    _currentWeather.postValue(t)
                    System.out.println(t)
                }

                override fun onError(e: Throwable) {
                    System.out.println(e)

                }

                override fun onComplete() {

                }

            })
    }
}