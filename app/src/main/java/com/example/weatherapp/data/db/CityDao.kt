package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CityDao {

    @Query("SELECT * FROM cityInfo ORDER BY id DESC")
    fun getAllCities(): List<City>?


    @Insert
    fun insertCity(city: City?)

    @Delete
    fun deleteCity(city: City?)

    @Query("SELECT * FROM cityInfo WHERE name LIKE :searchQuery ")
    fun searchCities(searchQuery: String): List<City>
}