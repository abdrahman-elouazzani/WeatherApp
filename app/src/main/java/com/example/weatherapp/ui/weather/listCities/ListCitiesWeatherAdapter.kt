package com.example.weatherapp.ui.weather.listCities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.db.City
import com.example.weatherapp.ui.weather.cityWeatherDetails.CityWeatherDetailsActivity

class ListCitiesWeatherAdapter(
    private var context: Context,
    private var viewModel: ListCitiesWeatherViewModel
    ): RecyclerView.Adapter<ListCitiesWeatherItemViewHolder>() {

     var listCities = mutableListOf<City>()

    fun setCitiesWeather(listCities: List<City>) {
        this.listCities = listCities.toMutableList()
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListCitiesWeatherItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_city_weather_item,parent,false)
        return ListCitiesWeatherItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListCitiesWeatherItemViewHolder, position: Int) {
        var city = listCities[position]
        holder.cityName.text = city.name
        holder.deleteAction.setOnClickListener({
            viewModel.deleteCity(city)
            notifyDataSetChanged()
        })
        holder.itemView.setOnClickListener({
            val intent = Intent(context,CityWeatherDetailsActivity::class.java)
            intent.putExtra("location",city.name)
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return listCities.size
    }
}

class ListCitiesWeatherItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cityName: TextView = itemView.findViewById(R.id.cityName)
    val deleteAction:ImageButton = itemView.findViewById(R.id.deleteAction)


}
