package com.example.weatherapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cityInfo")
data class City(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id : Int = 0,
    @ColumnInfo(name = "name")
    var name: String
)
