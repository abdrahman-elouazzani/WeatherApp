package com.example.weatherapp.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.network.NetworkStatus
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.data.repository.ForecastRepositoryImp
import io.reactivex.disposables.Disposable

class CurrentWeatherViewModel(val forecastRepository: ForecastRepository) : ViewModel() {
    private val _currentWeather: MutableLiveData<CurrentWeather> = MutableLiveData()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

     private val _networkStatus: MutableLiveData<NetworkStatus> = MutableLiveData()
     val networkStatus: LiveData<NetworkStatus> = _networkStatus

    fun getCurrentWeatherData(location: String) {
        forecastRepository.getCurrentWeather(location)
            .subscribe(object :io.reactivex.Observer<CurrentWeather>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CurrentWeather) {
                   _currentWeather.postValue(t)
                    _networkStatus.postValue(NetworkStatus("Success"))
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