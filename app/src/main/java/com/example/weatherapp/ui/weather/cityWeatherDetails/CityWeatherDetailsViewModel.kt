package com.example.weatherapp.ui.weather.cityWeatherDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.network.NetworkStatus
import com.example.weatherapp.data.repository.ForecastRepository
import io.reactivex.disposables.Disposable

class CityWeatherDetailsViewModel(val forecastRepository: ForecastRepository): ViewModel() {

    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()
    val networkStatus: LiveData<NetworkStatus> = _networkStatus

    /*
    * get the current weather bu location from the service
    * */

    fun getCurrentWeatherData(location: String) {
        forecastRepository.getCurrentWeather(location)
            .subscribe(object :io.reactivex.Observer<CurrentWeather>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CurrentWeather) {
                    _networkStatus.postValue(NetworkStatus("Success"))
                    _currentWeather.postValue(t)
                    System.out.println(t)
                }

                override fun onError(e: Throwable) {
                    System.out.println(e)
                    _networkStatus.postValue(NetworkStatus("error"))

                }

                override fun onComplete() {

                }

            })
    }
}