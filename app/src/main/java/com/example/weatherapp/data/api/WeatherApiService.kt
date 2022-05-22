package com.example.weatherapp.data.api

import com.example.weatherapp.data.model.CurrentWeather
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "7771eed8f9d2066a3ae2a476d7a944f1"

interface WeatherApiService {
  //  http://api.weatherstack.com/current?access_key=b303b4fece26f0d63725f69ce0d2de03&query=London

    @GET(value = "current")
    fun getCurrentWeather(
      @Query(value = "query") location: String
    ):io.reactivex.Observable<CurrentWeather>

  companion object {
    operator fun invoke(): WeatherApiService {
      // for add api_key in every query
      val requestInterceptor = Interceptor { chain ->

        val url = chain.request()
          .url()
          .newBuilder()
          .addQueryParameter("access_key", API_KEY)
          .build()
        val request = chain.request()
          .newBuilder()
          .url(url)
          .build()

        return@Interceptor chain.proceed(request)
      }

      val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(requestInterceptor)
        .build()

      return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://api.weatherstack.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)
    }
  }
}