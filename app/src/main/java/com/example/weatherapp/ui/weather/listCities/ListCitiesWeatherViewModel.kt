package com.example.weatherapp.ui.weather.listCities

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.db.City
import com.example.weatherapp.data.db.RoomDb

class ListCitiesWeatherViewModel(val context: Context) : ViewModel() {
    private var _listCities = MutableLiveData<List<City>>()
    val listCities : LiveData<List<City>> = _listCities


    fun getListCitiesObsserver(): LiveData<List<City>>{

        return listCities

    }

    fun getListCities() {
        val cityDao = RoomDb.getAppDatabase(context)?.cityDao()
        val list = cityDao?.getAllCities()
        _listCities.postValue(list!!)
    }

    fun insertCity(city: City) {
        val cityDao = RoomDb.getAppDatabase(context)?.cityDao()
        cityDao?.insertCity(city)
        getListCities()

    }

    fun deleteCity(city: City) {
        val cityDao = RoomDb.getAppDatabase(context)?.cityDao()
        cityDao?.deleteCity(city)
        getListCities()
    }

    fun searchCities(searchQuery: String) {
        val cityDao = RoomDb.getAppDatabase(context)?.cityDao()
        val list = cityDao?.searchCities(searchQuery)
        _listCities.postValue(list!!)

    }
}