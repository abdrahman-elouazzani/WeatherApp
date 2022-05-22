package com.example.weatherapp.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [City::class], version = 1)
abstract class RoomDb: RoomDatabase() {
    abstract fun cityDao():CityDao?

    companion object {
        private var INSTANCE: RoomDb? = null



        fun getAppDatabase(context: Context): RoomDb? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder(
                    context.applicationContext, RoomDb::class.java, "AppDBB"
                )
                    .addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // set default cities
                            val defaultList = listOf<String>(
                                "Casablanca","Rabat","Marrakech","Fes","Tangier"
                            )
                            for (city in defaultList) {
                                val contentValue = ContentValues()
                                contentValue.put("name",city)
                                db.insert("cityInfo", SQLiteDatabase.CONFLICT_IGNORE,contentValue)
                            }

                        }
                    })
                    .allowMainThreadQueries()
                    .build()

            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }


}