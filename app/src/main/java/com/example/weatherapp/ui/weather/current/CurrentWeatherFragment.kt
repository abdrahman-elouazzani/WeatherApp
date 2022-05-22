package com.example.weatherapp.ui.weather.current

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.repository.ForecastRepositoryImp
import com.example.weatherapp.databinding.CurrentWeatherFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient


class CurrentWeatherFragment : Fragment() {

    private lateinit var binding: CurrentWeatherFragmentBinding
    private lateinit var client: FusedLocationProviderClient

    val weatherApiService = WeatherApiService()

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       // client = LocationServices.getFusedLocationProviderClient(context!!)
        binding = CurrentWeatherFragmentBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, CurrentWeatherViewModelFactory(ForecastRepositoryImp(weatherApiService))).get(CurrentWeatherViewModel::class.java)
        viewModel.currentWeather.observe(this, {
              if (viewModel.networkStatus.value?.status.equals("Success"))
                  fetchData(it)
            else {
                Toast.makeText(context!!,"error connection to service",Toast.LENGTH_LONG).show()
            }
        })

       viewModel.getCurrentWeatherData("casablanca")

    }

    // update UI
    private fun fetchData(currentWeather: CurrentWeather) {

        binding.currentLocationLayout.cityName.text = currentWeather.location.name
        binding.currentLocationLayout.temp.text = currentWeather.current.temperature.toString()
        binding.currentLocationLayout.currentTime.text = currentWeather.location.localtime
        binding.currentLocationLayout.modeType.text = currentWeather.current.weatherDescriptions[0]
        binding.currentLocationLayout.windSpeed.text = "Wind Speed  ${currentWeather.current.windSpeed} km/h"
        binding.currentLocationLayout.pressure.text = "Pressure  ${currentWeather.current.pressure}"


    }


    private fun checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context!!,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
                    // Call method
                    getCurrentLocation();
        } else {
            // When permission is not granted
            // Call method
            requestPermissions(
                arrayOf(
                   android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check condition
        if (requestCode == 100 && (grantResults.size > 0)
            && (grantResults[0] + grantResults[1]
                    == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            getCurrentLocation()
        } else {
            // When permission are denied
            // Display toast
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    private fun getCurrentLocation() {
        // Initialize Location manager
        // Initialize Location manager
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {



        }


    }



}